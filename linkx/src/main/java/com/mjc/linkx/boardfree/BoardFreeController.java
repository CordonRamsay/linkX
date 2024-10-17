package com.mjc.linkx.boardfree;


import ch.qos.logback.core.model.Model;
import com.mjc.linkx.common.LoginAccessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.security.auth.login.LoginException;

@Slf4j
@Controller
@RequiredArgsConstructor

@RequestMapping("/boardFree")
public class BoardFreeController {

    @GetMapping("/board_list")
    public String boardList(){


        try{

        }catch(LoginAccessException ex){
            log.error(ex.toString());
            return "redirect:/login/login";
        }

        return "board_list";
    }
}
