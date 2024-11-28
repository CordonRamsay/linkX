package com.mjc.linkx.user;


import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import com.mjc.linkx.security.dto.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/session-login")
public class SessionLoginController {

    private final UserService userService;

    // 회원가입 페이지 이동
    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "login/join";
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");
        model.addAttribute("message", "");
        model.addAttribute("alertMessage", "");
        model.addAttribute("loginId", "");

        return "login/login";
    }

    @PostMapping("/login")
    public String login(@Validated @ModelAttribute LoginRequest loginRequest, Model model,
                        HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes) {

        try {
            model.addAttribute("loginType", "session-login");
            model.addAttribute("pageName", "세션 로그인");

            if (loginRequest == null) {
                model.addAttribute("message", "아이디 와 비밀번호를 입력하세요.");
                return "login/login";
            }
            IUser user = userService.login(loginRequest);

            // ID와 PW에 해당하는 회원정보가 없을 경우
            if (user == null) {
                model.addAttribute("message", "아이디 또는 비밀번호가 틀립니다.");
                model.addAttribute("loginId",  loginRequest.getLoginId());
                return "login/login";
            }else{
                // 세션을 생성하기 전에 기존의 세션 파기
                httpServletRequest.getSession().invalidate();
                HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
                // 세션에 userId를 넣어줌
                session.setAttribute("LoginUser",user);
                session.setAttribute("userId", user.getId());
                // 리디렉션 시 alertMessage 전달
                redirectAttributes.addFlashAttribute("alertMessage", "로그인 되었습니다.");
                session.setMaxInactiveInterval(7200); // 세션 2시간동안 유지

                return "redirect:/";
            }
        } catch (Exception ex) {
            log.error(ex.toString());
            model.addAttribute("message", "서버 오류가 발생했습니다. 다시 시도해주세요.");
            return "login/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }

    // 사용자 정보 페이지
    @GetMapping("/info")
    public String userInfo(@SessionAttribute(name = "userId", required = false) Long userId, Model model) {
        try {
            model.addAttribute("loginType", "session-login");
            model.addAttribute("pageName", "세션 로그인");

            UserDto loginUser = userService.getLoginUserById(userId);

            if (loginUser == null) {
                return "redirect:/session-login/login";
            }

            model.addAttribute("user", loginUser);

        } catch (Exception ex) {
            log.error(ex.toString());
        }
        return "login/info";

    }

    // 관리자 페이지 이동
    @GetMapping("/admin")
    public String adminPage(@SessionAttribute(name = "userId", required = false) Long userId, Model model)  {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        UserDto loginUser = this.userService.getLoginUserById(userId);

        if (loginUser == null) {
            return "redirect:/session-login/login";
        }
        if(!loginUser.getRole().equals(UserRole.ADMIN)) {
            return "redirect:/";
        }
        return "login/admin";
    }
}
