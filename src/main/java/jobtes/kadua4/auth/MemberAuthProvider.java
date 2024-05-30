package jobtes.kadua4.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import jobtes.kadua4.services.auth.MemberDetailsService;

@Component
public class MemberAuthProvider implements AuthenticationProvider{
@Autowired
    private MemberDetailsService memberDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            UserDetails memberDetails = memberDetailsService.loadUserByUsername(authentication.getName());
            return new UsernamePasswordAuthenticationToken(memberDetails.getUsername(),memberDetails.getPassword(),memberDetails.getAuthorities());
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
