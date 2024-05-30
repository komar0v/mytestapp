package jobtes.kadua4.controller;
import java.io.IOException;
import java.util.Base64;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import jobtes.kadua4.model.MemberMDL;
import jobtes.kadua4.services.MemberService;


//CONTROLLER KHUSUS MEMBER
@Controller
@RequestMapping("/member/profile")
public class OnlyMemberController {
@Autowired
    private MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public OnlyMemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/me")
    public String showDetails(Model model,HttpSession session) {
        String memberMail= session.getAttribute("email").toString();

        MemberMDL member = memberService.getMemberByEmail(memberMail);
        byte[] imageData = member.getFoto_diri();
        if (imageData!=null) {
            String base64image = Base64.getEncoder().encodeToString(imageData);
            model.addAttribute("base64image", base64image);
        }
        model.addAttribute("memberData", member);
        return "wrapper/memberwrapper/profilepage";
    }

    @GetMapping("/edit")
    public String showEditForm(Model model,HttpSession session) {
        String memberMail= session.getAttribute("email").toString();

        MemberMDL member = memberService.getMemberByEmail(memberMail);
        byte[] imageData = member.getFoto_diri();
        if (imageData!=null) {
            String base64image = Base64.getEncoder().encodeToString(imageData);
            model.addAttribute("base64image", base64image);
        }
        model.addAttribute("memberData", member);
        return "wrapper/memberwrapper/editform";
    }

    @PostMapping("/edit/{id}")
    public String editMember(@PathVariable("id") UUID id, @ModelAttribute("member") MemberMDL member) {
        MemberMDL existingMemberData = memberService.getMemberById(id);
        if (member.getPassword() == null || member.getPassword().isEmpty()) {
            member.setPassword(existingMemberData.getPassword());
        }
        if (member.getFoto_diri() == null) {
            member.setFoto_diri(existingMemberData.getFoto_diri());
        }
        if (member.getTanggal_lahir() == null) {
            member.setTanggal_lahir(existingMemberData.getTanggal_lahir());
        }
        member.setId(id);
        String rawPassword = member.getPassword();
        String password = passwordEncoder.encode(rawPassword);
        member.setPassword(password);
        memberService.updateMember(id, member);
        return "redirect:/member/profile/me";
    }

    @PostMapping("/upload/{id}")
    public String uploadProfilePhoto(@PathVariable("id") UUID id, @RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return "redirect:/error";
        }

        if (file.getSize() > 1 * 1024 * 1024) { // 1MB limit
            return "redirect:/error";
        }

        try {
            byte[] photoBytes = file.getBytes();
            memberService.savePhoto(id, photoBytes); 
        } catch (IOException e) {
            e.printStackTrace();
            return "redirect:/error";
        }

        return "redirect:/member/profile/me";
    }
}
