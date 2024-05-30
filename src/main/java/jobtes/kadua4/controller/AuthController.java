package jobtes.kadua4.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {
    @GetMapping("/admin/login")
    public String adminLogin() {
        return "loginpage/adminlogin";
    }

    @GetMapping("/admin/home")
    public String adminHome() {
        return "adminpages/home/adminhome";
    }

    @GetMapping("/member/login")
    public String memberLogin() {
        return "loginpage/memberlogin";
    }

    @GetMapping("/member/home")
    public String memberHome() {
        return "memberpages/home/memberhome";
    }
}
