package com.mjc.linkx.petition;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j

public class PetitionController {

    private final IPetitionService petitionService;

    @GetMapping("/save")                //글 작성하기 위한 메소드 html과 같은 이름으로 호출되어야 한다. 영상에선 form에서 action을 save로 요청함
    public String save() {
        return "save";
    }

    @PostMapping("/save")               //게시글 데이터 전송
    public String save(PetitionDto petitionDto) {       //dto에서 이름이 정확히 일치해야 한다.
        System.out.println(petitionDto);
        IPetitionService.save(petitionDto);
        return "index";
    }

}
