package jobtes.kadua4.controller;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

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
    public String adminHome(Principal principal, HttpSession session) {
        String username = principal.getName();
        session.setAttribute("username", username);
        return "wrapper/adminpanel";
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
