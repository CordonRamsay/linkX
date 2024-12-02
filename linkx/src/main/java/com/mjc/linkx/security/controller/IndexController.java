package com.mjc.linkx.security.controller;


import com.mjc.linkx.boardfree.BoardFreeDto;
import com.mjc.linkx.boardfree.IBoardFreeService;
import com.mjc.linkx.petition.IPetitionService;
import com.mjc.linkx.petition.PetitionDto;
import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserDto;
import com.mjc.linkx.user.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.List;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;
    private final IBoardFreeService boardFreeService;
    private final IPetitionService petitionService;

    @GetMapping("")
    public String index(Model model, @SessionAttribute(name = "userId", required = false) Long userId, HttpSession session) {


        if (userId != null) {
            IUser loginUser = this.userService.getLoginUserById(userId);
            session.setAttribute("LoginUser", loginUser);  // 세션에 저장

        }
        UserDto user = this.userService.getLoginUserById(userId);

        if (user != null) {
            model.addAttribute("nickname", user.getNickname());
            model.addAttribute("major",user.getMajorName());
        }

        List<BoardFreeDto> recently = this.boardFreeService.findRecently();
        List<BoardFreeDto> viewTop = this.boardFreeService.findViewTop();
        model.addAttribute("recently", recently);
        model.addAttribute("viewTop", viewTop);


        //청원 인기 글 가져오기,동의자 수 상위 5개 청원 가져오기
        List<PetitionDto> hotAgreedPetitions = this.petitionService.findHotAgreedPetitions();
        // 가져온 데이터 로그 출력
        System.out.println("=== Hot Agreed Petitions ===");
        hotAgreedPetitions.forEach(p -> {
            System.out.println("ID: " + p.getId());
            System.out.println("Title: " + p.getPetiTitle());
            System.out.println("Content: " + p.getPetiContent());
            System.out.println("Days Left: " + p.getDaysLeft());
            System.out.println("-----------------------------");
        });
        model.addAttribute("hotAgreedPetitions", hotAgreedPetitions);



        return "index";
    }
}
