package com.mjc.linkx.taste;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/taste/data")
public class TasteRestController {

    @Autowired
    private TasteService tasteService;

    @GetMapping("")
    public String taste() {
        return tasteService.fetchAndSaveTasteData();  // 데이터 저장 로직을 서비스로 위임
    }

    @GetMapping("/list")
    public List<TasteRestDto> getTasteList() {
        return tasteService.getTasteList();  // 데이터 조회 로직을 서비스로 위임
    }
}
