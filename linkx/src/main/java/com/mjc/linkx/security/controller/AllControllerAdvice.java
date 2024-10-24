package com.mjc.linkx.security.controller;

import com.mjc.linkx.user.IUser;
import com.mjc.linkx.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttribute;

import java.util.Arrays;


@ControllerAdvice   // 모든 URL 요청을 가로채서 처리한다.
public class AllControllerAdvice {
    @Autowired
    private UserService userService;

    private final String[] authUrls = new String[]{
             "/boardFree"
            , "/boardDept"
            , "/session-login"
            ,"/api/v1/boardfree"

    };


    @ModelAttribute // @ControllerAdvice, @ModelAttribute 이 단어가 있어야지만 모든 주소 요청시 가로챌수 있다.
    public void addModel( HttpServletRequest request, Model model
            ,@SessionAttribute(name = "userId", required =false ) Long userId ) {

        String url = request.getRequestURI();
        String bFind = Arrays.stream(this.authUrls)
                .filter(url::contains).findFirst().orElse(null);
        if ( bFind != null && userId != null ) {
            IUser loginUser = this.userService.getLoginUserById(userId);
            model.addAttribute("LoginUser", loginUser);
            System.out.println("LoginUser in Advice: " + loginUser); // 로그로 확인
        } else {
            System.out.println("No user found or URL not matched."); // 디버깅용 로그
        }
    }
}
