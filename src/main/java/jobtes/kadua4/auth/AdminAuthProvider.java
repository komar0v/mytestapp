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

import jobtes.kadua4.services.auth.AdminDetailsService;

@Component
public class AdminAuthProvider implements AuthenticationProvider{
@Autowired
    private AdminDetailsService admindetailservice;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        try{
            UserDetails adminDetails = admindetailservice.loadUserByUsername(authentication.getName());
            return new UsernamePasswordAuthenticationToken(adminDetails.getUsername(),adminDetails.getPassword(),adminDetails.getAuthorities());
        }catch (UsernameNotFoundException e){
            throw new BadCredentialsException("Invalid Credentials");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
