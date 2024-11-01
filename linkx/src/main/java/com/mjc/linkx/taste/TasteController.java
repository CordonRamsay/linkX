package com.mjc.linkx.taste;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/taste")
public class TasteController {

    private final ITasteRestMyBatisMapper tasteMapper;

    // 생성자를 통한 의존성 주입
    public TasteController(ITasteRestMyBatisMapper tasteMapper) {
        this.tasteMapper = tasteMapper;
    }

    @GetMapping("")
    public String taste(Model model) {
        // 데이터베이스에서 맛집 데이터를 가져옵니다.
        List<TasteRestDto> restaurants = tasteMapper.setMapMaker();

        // 모델에 데이터를 추가하여 뷰로 전달
        model.addAttribute("restaurants", restaurants);

        // 로그로 데이터 확인
        log.info("Fetched restaurants: {}", restaurants);

        // "taste.html" 뷰 반환
        return "taste";
    }
}
