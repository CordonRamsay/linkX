package com.mjc.linkx.taste;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/taste/data")
public class TasteRestController {

    @Autowired
    private ITasteRestMyBatisMapper tasteRestMapper;

    @GetMapping("")
    public String taste() {
        String query = "명지전문대 음식점";

        // URI 생성 및 인코딩 강제
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

        // JSON 데이터를 파싱하여 DTO 리스트 생성
        List<TasteRestDto> tasteRestDtos = parseJsonToDtoList(result.getBody());

        // 데이터베이스에 삽입
        for (TasteRestDto dto : tasteRestDtos) {
            tasteRestMapper.insert(dto);
        }

        return "데이터 저장 완료";
    }

    // JSON 데이터를 TasteRestDto 리스트로 변환
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
                dto.setMapX(itemNode.path("mapx").asText()); // mapx와 mapy를 String으로 저장
                dto.setMapY(itemNode.path("mapy").asText());
                dtos.add(dto);
            }
        } catch (Exception e) {
            log.error("JSON 파싱 오류: ", e);
        }
        return dtos;
    }
}