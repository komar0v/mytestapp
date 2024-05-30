package jobtes.kadua4.controller;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import jobtes.kadua4.model.AdminMDL;
import jobtes.kadua4.services.AdminService;

@Controller
@RequestMapping("/admin/manage-admins")
public class AdminController {
@Autowired
    private AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    public AdminController(AdminService adminService, PasswordEncoder passwordEncoder) {
        this.adminService = adminService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin-list")
    public String listAdministrators(Model model) {
        model.addAttribute("administrators", adminService.getAllAdmins());
        return "wrapper/manage_admin/appadminmanage";
    }

    @GetMapping("/show-details/{id}")
    public String showDetails(@PathVariable("id") UUID id, Model model) {
        AdminMDL administrator = adminService.getAdminById(id);
        model.addAttribute("administrator", administrator);
        return "wrapper/manage_admin/appadmindetails";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("administrator", new AdminMDL());
        return "wrapper/manage_admin/appadminadd";
    }

    @PostMapping("/add")
    public String addAdministrator(@ModelAttribute("administrator") AdminMDL administrator) {
        String rawPassword = administrator.getPassword();
        String password = passwordEncoder.encode(rawPassword);
        administrator.setRole("ADMIN");
        administrator.setCreatedAt(LocalDateTime.now());
        administrator.setPassword(password);
        adminService.createAdmin(administrator);
        return "redirect:/admin/manage-admins/admin-list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        AdminMDL administrator = adminService.getAdminById(id);
        model.addAttribute("administrator", administrator);
        return "wrapper/manage_admin/appadminedit";
    }

    @PostMapping("/edit/{id}")
    public String editAdministrator(@PathVariable("id") UUID id, @ModelAttribute("administrator") AdminMDL administrator) {
        AdminMDL existingAdmin = adminService.getAdminById(id);
        if (administrator.getPassword() == null || administrator.getPassword().isEmpty()) {
            administrator.setPassword(existingAdmin.getPassword());
        }        
        administrator.setId(id);
        String rawPassword = administrator.getPassword();
        String password = passwordEncoder.encode(rawPassword);
        administrator.setPassword(password);
        adminService.updateAdmin(id, administrator);
        return "redirect:/admin/manage-admins/admin-list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdministrator(@PathVariable("id") UUID id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/manage-admins/admin-list";
    }
}
