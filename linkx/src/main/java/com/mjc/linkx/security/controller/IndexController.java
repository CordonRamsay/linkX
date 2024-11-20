package com.mjc.linkx.security.controller;


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

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class IndexController {

    private final UserService userService;

    @GetMapping("")
    public String index(Model model, @SessionAttribute(name = "userId", required = false) Long userId, HttpSession session) {


        if (userId != null) {
            IUser loginUser = this.userService.getLoginUserById(userId);
            session.setAttribute("LoginUser", loginUser);  // 세션에 저장

        }
        UserDto user = this.userService.getLoginUserById(userId);

        if (user != null) {
            model.addAttribute("nickname", user.getNickname());
        }

        return "index2";
    }
}
