// TasteServiceImpl.java
package com.mjc.linkx.taste;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        String query = "명지대 음식점";
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
            tasteRestMapper.insert(dto);
        }

        return "데이터 저장 완료";
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
    public List<TasteReviewDto> getReviewsByRestaurantId(Long restId) {
        return tasteRestMapper.getReviewsByRestaurantId(restId);
    }

    @Override
    public void addReview(TasteReviewDto reviewDto) {
        tasteRestMapper.insertReview(reviewDto);
    }

}
