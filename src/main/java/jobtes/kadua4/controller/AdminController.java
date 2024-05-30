package jobtes.kadua4.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import jobtes.kadua4.model.AdminMDL;
import jobtes.kadua4.services.AdminService;

@RequestMapping("/admin/dashboard")
public class AdminController {
@Autowired
    private AdminService adminService;

    @GetMapping("/list")
    public String listAdministrators(Model model) {
        model.addAttribute("administrators", adminService.getAllAdmins());
        return "wrapper/appadminmanage";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("administrator", new AdminMDL());
        return "admin/add";
    }

    @PostMapping("/add")
    public String addAdministrator(@ModelAttribute("administrator") AdminMDL administrator) {
        adminService.createAdmin(administrator);
        return "redirect:/admin/list";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") UUID id, Model model) {
        AdminMDL administrator = adminService.getAdminById(id);
        model.addAttribute("administrator", administrator);
        return "admin/edit";
    }

    @PostMapping("/edit/{id}")
    public String editAdministrator(@PathVariable("id") UUID id, @ModelAttribute("administrator") AdminMDL administrator) {
        administrator.setId(id);
        adminService.updateAdmin(id, administrator);
        return "redirect:/admin/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteAdministrator(@PathVariable("id") UUID id) {
        adminService.deleteAdmin(id);
        return "redirect:/admin/list";
    }
}
