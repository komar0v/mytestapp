package jobtes.kadua4.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class AuthController {
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "loginpage/adminlogin";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/admin/login";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "adminpages/home/adminhome";
    }

    @GetMapping("/member/login")
    public String memberLogin() {
        return "loginpage/memberlogin";
    }

    @GetMapping("/member/logout")
    public String logoutMember(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/member/login";
    }

    @GetMapping("/member/home")
    public String memberHome() {
        return "memberpages/home/memberhome";
    }
}
