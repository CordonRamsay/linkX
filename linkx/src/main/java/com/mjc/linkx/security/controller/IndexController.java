package com.mjc.linkx.security.controller;


import com.mjc.linkx.boarddept.BoardDeptDto;
import com.mjc.linkx.boarddept.IBoardDeptService;
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
    private final IBoardDeptService boardDeptService;
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
        // 자유게시판 상위글5개
        List<BoardFreeDto> recentlyFree = this.boardFreeService.findRecently();
        model.addAttribute("recentlyFree", recentlyFree);

        // 학과게시판 상위글5개
        List<BoardDeptDto> recentlyDept = this.boardDeptService.findRecently();
        model.addAttribute("recentlyDept", recentlyDept);

        // 인기글 3개
        List<BoardFreeDto> viewTop = this.boardFreeService.findViewTop();
        model.addAttribute("viewTop", viewTop);

        //청원 인기 글 가져오기,동의자 수 상위 5개 청원 가져오기
        List<PetitionDto> hotAgreedPetitions = this.petitionService.findHotAgreedPetitions();
        model.addAttribute("hotAgreedPetitions", hotAgreedPetitions);



        return "index";
    }
}
