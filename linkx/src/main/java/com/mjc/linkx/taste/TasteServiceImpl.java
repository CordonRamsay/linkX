package com.mjc.linkx.taste;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mjc.linkx.user.UserDto;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
public class TasteServiceImpl implements TasteService {

    @Autowired
    private ITasteRestMyBatisMapper tasteRestMapper;

    @Override
    public String fetchAndSaveTasteData() {
        String query = "명지전문대학 은행";
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
        List<TasteRestDto> tasteRestDtos = parseJsonToDtoList(result.getBody());

        for (TasteRestDto dto : tasteRestDtos) {
            log.debug("Parsed DTO: {}", dto); // DTO의 내용을 콘솔에 출력
            tasteRestMapper.insert(dto);
        }

        return result.getBody();
    }

    private List<TasteRestDto> parseJsonToDtoList(String json) {
        List<TasteRestDto> dtos = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode itemsNode = objectMapper.readTree(json).path("items");
            for (JsonNode itemNode : itemsNode) {
                TasteRestDto dto = new TasteRestDto();
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
    public List<TasteRestDto> getTasteList() {
        return tasteRestMapper.getTasteList();
    }

    @Override
    public List<TasteReviewDto> getReviewsByRestaurantId(Long restId, HttpSession session) {
        List<TasteReviewDto> reviews = tasteRestMapper.getReviewsByRestaurantId(restId);
        Long currentUserId = getCurrentUserIdFromSession(session); // 세션에서 현재 사용자 ID 가져오기

        for (TasteReviewDto review : reviews) {
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
    public void addReview(TasteReviewDto reviewDto) {
        try {
            tasteRestMapper.insertReview(reviewDto);
        } catch (Exception e) {
            log.error("리뷰 추가 중 오류 발생: ", e);
            throw new RuntimeException("리뷰를 추가할 수 없습니다.");
        }
    }

    @Override
    public void deleteReview(Long reviewId, Long userId) throws Exception {
        TasteReviewDto review = tasteRestMapper.getReviewById(reviewId);
        if (review == null) {
            log.warn("삭제하려는 리뷰가 존재하지 않습니다. 리뷰 ID: {}", reviewId);
            throw new Exception("리뷰가 존재하지 않습니다.");
        }
        if (!review.getUserId().equals(userId)) {
            log.warn("리뷰 삭제 권한이 없습니다. 리뷰 ID: {}, 요청 사용자 ID: {}", reviewId, userId);
            throw new Exception("삭제 권한이 없습니다.");
        }
        try {
            tasteRestMapper.deleteReviewById(reviewId);
            log.info("리뷰가 삭제되었습니다. 리뷰 ID: {}", reviewId);
        } catch (Exception e) {
            log.error("리뷰 삭제 중 오류 발생: ", e);
            throw new Exception("리뷰를 삭제할 수 없습니다.");
        }
    }
}
