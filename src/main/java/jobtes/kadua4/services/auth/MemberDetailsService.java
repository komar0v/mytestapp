package jobtes.kadua4.services.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jobtes.kadua4.model.MemberMDL;
import jobtes.kadua4.repos.MemberRepository;

@Service
public class MemberDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Autowired
    public MemberDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberMDL member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Member not found"));
        System.out.println("{member:"+member.getEmail()+", id:"+member.getId()+", role:"+member.getRole()+"}"); //debugging
        return User
                .withUsername(member.getEmail())
                .password(member.getPassword())
                .roles(member.getRole())
                .build();
    }
}
