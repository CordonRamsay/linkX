package com.mjc.linkx.user;


import com.mjc.linkx.security.dto.JoinRequest;
import com.mjc.linkx.security.dto.LoginRequest;
import com.mjc.linkx.security.dto.UserRole;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/session-login")
public class SessionLoginController {

    private final UserService userService;

    @GetMapping(value = {"", "/"})
    public String home(Model model, @SessionAttribute(name="userId", required = false)Long userId) {
        try {
            model.addAttribute("loginType", "session-login");
            model.addAttribute("pageName", "세션 로그인");

           UserDto user = this.userService.getLoginUserById(userId);

            if (user != null) {
                model.addAttribute("nickname", user.getNickname());
            }
        } catch (Exception ex) {
            log.error(ex.toString());
        }

        return "login/home";
    }
    // 회원가입 페이지 이동
    @GetMapping("/join")
    public String joinPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        model.addAttribute("joinRequest", new JoinRequest());
        return "login/join";
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String join(@Valid @ModelAttribute JoinRequest joinRequest,
                       BindingResult bindingResult, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        // 중복 체크: 로그인 아이디
        if (userService.checkLoginIdDuplicate(joinRequest.getLoginId())) {
            bindingResult.addError(new FieldError("joinRequest", "loginId", "로그인 아이디가 중복됩니다."));
        }
        // 중복 체크: 닉네임
        if (userService.checkNicknameDuplicate(joinRequest.getNickname())) {
            bindingResult.addError(new FieldError("joinRequest", "nickname", "닉네임이 중복됩니다."));
        }
        // 비밀번호 확인 체크
        if (!joinRequest.getPassword().equals(joinRequest.getPasswordCheck())) {
            bindingResult.addError(new FieldError("joinRequest", "passwordCheck", "비밀번호가 일치하지 않습니다."));
        }

        if (bindingResult.hasErrors()) {
            return "login/join";
        }

        userService.join(joinRequest);  // 회원가입 처리
        return "redirect:/session-login";
    }

    // 로그인 페이지 이동
    @GetMapping("/login")
    public String loginPage(Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");


        return "login/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginRequest loginRequest, Model model,
                        HttpServletRequest httpServletRequest, BindingResult bindingResult) {

        try {
            model.addAttribute("loginType", "session-login");
            model.addAttribute("pageName", "세션 로그인");

            IUser user = userService.login(loginRequest);

            if (user == null) {
                bindingResult.reject("loginFail", "로그인 아이디 또는 비밀번호가 틀렸습니다.");
            }
            if (bindingResult.hasErrors()) {
                return "login/login";
            }

            // 세션을 생성하기 전에 기존의 세션 파기
            httpServletRequest.getSession().invalidate();
            HttpSession session = httpServletRequest.getSession(true);  // Session이 없으면 생성
            // 세션에 userId를 넣어줌
            session.setAttribute("userId", user.getId());
            session.setMaxInactiveInterval(3600); // Session이 1시간동안 유지

        } catch (Exception ex) {
            log.error(ex.toString());
            return "login/login";
        }
        return "redirect:/session-login";
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, Model model) {
        model.addAttribute("loginType", "session-login");
        model.addAttribute("pageName", "세션 로그인");

        HttpSession session = request.getSession(false);  // Session이 없으면 null return
        if(session != null) {
            session.invalidate();
        }
        return "redirect:/session-login";
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
            return "redirect:/session-login";
        }
        return "login/admin";
    }
}
