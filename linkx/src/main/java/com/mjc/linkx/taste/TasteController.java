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

    @GetMapping("")
    public String taste(Model model) {

        return "taste";
    }
}
