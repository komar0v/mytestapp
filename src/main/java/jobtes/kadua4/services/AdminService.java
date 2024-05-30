package jobtes.kadua4.services;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jobtes.kadua4.model.AdminMDL;
import jobtes.kadua4.repos.AdminRepository;

@Service
public class AdminService {

@Autowired
    private AdminRepository adminRepository;

    @Autowired
    public AdminService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    public List<AdminMDL> getAllAdmins() {
        return adminRepository.findAll();
    }

    public AdminMDL getAdminById(UUID id) {
        return adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));
    }

    public AdminMDL createAdmin(AdminMDL admin) {
        return adminRepository.save(admin);
    }

    public AdminMDL updateAdmin(UUID id, AdminMDL adminDetails) {
        AdminMDL admin = adminRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        admin.setUsername(adminDetails.getUsername());
        admin.setPassword(adminDetails.getPassword());

        return adminRepository.save(admin);
    }

    public void deleteAdmin(UUID id) {
        adminRepository.deleteById(id);
    }
}
