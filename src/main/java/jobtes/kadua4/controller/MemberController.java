package jobtes.kadua4.controller;

import java.time.LocalDateTime;
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

import jobtes.kadua4.model.AdminMDL;
import jobtes.kadua4.model.MemberMDL;
import jobtes.kadua4.services.MemberService;

@Controller
@RequestMapping("/admin/manage-members")
public class MemberController {
@Autowired
    private MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    public MemberController(MemberService memberService, PasswordEncoder passwordEncoder) {
        this.memberService = memberService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/member-list")
    public String listMembers(Model model) {
        model.addAttribute("members", memberService.getAllMembers());
        return "wrapper/manage_members/appmembermanage";
    }

    @GetMapping("/show-details/{id}")
    public String showDetails(@PathVariable("id") UUID id, Model model) {
        MemberMDL member = memberService.getMemberById(id);
        byte[] imageData = member.getFoto_diri();
        if (imageData!=null) {
            String base64image = Base64.getEncoder().encodeToString(imageData);
            model.addAttribute("base64image", base64image);
        }
        model.addAttribute("memberData", member);
        return "wrapper/manage_members/appmemberdetails";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("member", new MemberMDL());
        return "wrapper/manage_members/appmemberadd";
    }

    @GetMapping("/delete/{id}")
    public String deleteMember(@PathVariable("id") UUID id) {
        memberService.deleteMember(id);
        return "redirect:/admin/manage-members/member-list";
    }

    @PostMapping("/add")
    public String addAdministrator(@ModelAttribute("member") MemberMDL member) {
        String rawPassword = member.getPassword();
        String password = passwordEncoder.encode(rawPassword);
        member.setRole("MEMBER");
        member.setEmail(member.getEmail());
        
        member.setFoto_diri(null);
        member.setCreatedAt(LocalDateTime.now());
        member.setNo_hp(member.getNo_hp());
        member.setPassword(password);
        memberService.createMember(member);
        return "redirect:/admin/manage-members/member-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        MemberMDL member = memberService.getMemberById(id);
        model.addAttribute("member", member);
        return "wrapper/manage_members/appmemberedit";
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
        return "redirect:/admin/manage-members/member-list";
    }
}
