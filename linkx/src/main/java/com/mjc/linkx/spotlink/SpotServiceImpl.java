package com.mjc.linkx.spotlink;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.linkx.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SpotServiceImpl implements SpotService {

    @Autowired
    private ISpotMyBatisMapper spotMapper;

    @Override
    public String fetchAndSaveTasteData() {
        String query = "명지전문대학";
        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/local.json")
                .queryParam("query", query)
                .queryParam("display", 5)
                .queryParam("start", 1)
                .queryParam("sort", "random")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "AWhC4hyaemuGWN5ZutWr")
                .header("X-Naver-Client-Secret", "Tarvc6gJdA")
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        List<SpotDto> spotDtos = parseJsonToDtoList(result.getBody());

        for (SpotDto dto : spotDtos) {
            log.debug("Parsed DTO: {}", dto); // DTO의 내용을 콘솔에 출력
            spotMapper.insert(dto);
        }

        return result.getBody();
    }

    @Cacheable(value = "images", key = "#query")
    public String searchImage(String query) {
        try {
            // API 호출 전 1000ms 딜레이 추가
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }

        URI uri = UriComponentsBuilder
                .fromUriString("https://openapi.naver.com")
                .path("/v1/search/image.json")
                .queryParam("query", query)
                .queryParam("display", 1)
                .queryParam("start", 1)
                .queryParam("sort", "sim")
                .encode()
                .build()
                .toUri();

        RestTemplate restTemplate = new RestTemplate();
        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("X-Naver-Client-Id", "AWhC4hyaemuGWN5ZutWr")
                .header("X-Naver-Client-Secret", "Tarvc6gJdA")
                .build();

        try {
            ResponseEntity<String> result = restTemplate.exchange(req, String.class);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode rootNode = objectMapper.readTree(result.getBody());
            JsonNode itemsNode = rootNode.path("items");

            if (itemsNode.isArray() && !itemsNode.isEmpty()) {
                JsonNode firstItem = itemsNode.get(0);
                return firstItem.path("link").asText(); // 이미지 URL 반환
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 이미지가 없으면 기본 이미지 반환
        return "/img/default.png";
    }

    private List<SpotDto> parseJsonToDtoList(String json) {
        List<SpotDto> dtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode itemsNode = objectMapper.readTree(json).path("items");
            for (JsonNode itemNode : itemsNode) {
                SpotDto dto = new SpotDto();
                dto.setTitle(itemNode.path("title").asText());
                dto.setLink(itemNode.path("link").asText());
                dto.setCategory(itemNode.path("category").asText());
                dto.setAddr(itemNode.path("address").asText());
                dto.setRoadAddr(itemNode.path("roadAddress").asText());
                dto.setMapX(itemNode.path("mapx").asText());
                dto.setMapY(itemNode.path("mapy").asText());
                dtos.add(dto);
            }
        } catch (Exception e) {
            log.error("JSON 파싱 오류: ", e);
        }
        return dtos;
    }

    @Override
    public List<SpotDto> getSpotList() {
        return spotMapper.getSpotList();
    }

    @Override
    public List<SpotReviewDto> getReviewsBySpotId(Long spotId, HttpSession session) {
        List<SpotReviewDto> reviews = spotMapper.getReviewsBySpotId(spotId);
        Long currentUserId = getCurrentUserIdFromSession(session); // 세션에서 현재 사용자 ID 가져오기

        for (SpotReviewDto review : reviews) {
            log.info("리뷰 작성자 ID: {}, 현재 사용자 ID: {}", review.getUserId(), currentUserId);
            review.setCanDelete(review.getUserId() != null && review.getUserId().equals(currentUserId));
        }
        return reviews;
    }

    private Long getCurrentUserIdFromSession(HttpSession session) {
        Object loginUser = session.getAttribute("LoginUser");
        if (loginUser == null) {
            log.warn("세션에 로그인 정보가 없습니다.");
            return null;
        }
        log.info("현재 로그인한 사용자 ID: {}", ((UserDto) loginUser).getId());
        return ((UserDto) loginUser).getId();
    }



    @Override
    public void addReview(SpotReviewDto reviewDto) {
        try {
            spotMapper.insertReview(reviewDto);
        } catch (Exception e) {
            log.error("리뷰 추가 중 오류 발생: ", e);
            throw new RuntimeException("리뷰를 추가할 수 없습니다.");
        }
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        SpotReviewDto review = spotMapper.getReviewById(reviewId);
        if (review == null) {
            log.warn("삭제하려는 리뷰가 존재하지 않습니다. 리뷰 ID: {}", reviewId);
            throw new Exception("리뷰가 존재하지 않습니다.");
        }
        if (!review.getUserId().equals(userId)) {
            log.warn("리뷰 삭제 권한이 없습니다. 리뷰 ID: {}, 요청 사용자 ID: {}", reviewId, userId);
            throw new Exception("삭제 권한이 없습니다.");
        }
        try {
            spotMapper.deleteReviewById(reviewId);
            log.info("리뷰가 삭제되었습니다. 리뷰 ID: {}", reviewId);
        } catch (Exception e) {
            log.error("리뷰 삭제 중 오류 발생: ", e);
            throw new Exception("리뷰를 삭제할 수 없습니다.");
        }
    }
    @Override
    public SpotDto getSpotById(Long spotId) {
        return spotMapper.getSpotById(spotId);
    }

}
