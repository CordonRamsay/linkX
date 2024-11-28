package com.mjc.linkx.taste;

import com.mjc.linkx.common.exception.LoginAccessException;
import com.mjc.linkx.user.IUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Slf4j
@Controller
@RequestMapping("/taste")
public class TasteController {

    @GetMapping("")
    public String taste(Model model, HttpSession session) {

        try {
            // 세션에서 LoginUser 확인
            IUser loginUser = (IUser) session.getAttribute("LoginUser");

            // 로그인이 되어있으면 nickname을 화면으로 전달, 로그인 안 되어있으면 예외 처리
            if (loginUser != null) {
                model.addAttribute("nickname", loginUser.getNickname());
                session.setAttribute("LoginUser", loginUser);
            } else {
                throw new LoginAccessException("로그인을 해주세요");
            }

        } catch (LoginAccessException ex) {
            log.error(ex.toString());
            return "redirect:/login/login";
        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "taste";
    }
}
