package jobtes.kadua4.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jobtes.kadua4.model.AdminMDL;
import jobtes.kadua4.repos.AdminRepository;

@Service
public class AdminDetailsService implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Autowired
    public AdminDetailsService(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminMDL admin = adminRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Admin not found"));
        System.out.println("{username:"+admin.getUsername()+", id:"+admin.getId()+", role:"+admin.getRole()+"}"); //debugging
        UserDetails user = User.builder()
            .username(admin.getUsername())
            .password(admin.getPassword())
            .roles(admin.getRole())
            .build();
        return user;
    }
}
