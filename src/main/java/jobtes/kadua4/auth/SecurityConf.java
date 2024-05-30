package jobtes.kadua4.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import jobtes.kadua4.services.auth.AdminDetailsService;
import jobtes.kadua4.services.auth.MemberDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConf {

    private final AdminDetailsService adminDetailsService;
    private final MemberDetailsService memberDetailsService;
    private final AdminAuthProvider adminAuthProvider;
    private final MemberAuthProvider memberAuthProvider;

    public SecurityConf(AdminDetailsService adminDetailsService, MemberDetailsService memberDetailsService, AdminAuthProvider adminAuthProvider, MemberAuthProvider memberAuthProvider) {
        this.adminDetailsService = adminDetailsService;
        this.memberDetailsService = memberDetailsService;
        this.adminAuthProvider = adminAuthProvider;
        this.memberAuthProvider = memberAuthProvider;
    }

    @Bean
    public SecurityFilterChain adminSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/admin/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin/login").permitAll()
                        .anyRequest().hasRole("ADMIN"))
                .formLogin(form -> form
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/login")
                        .defaultSuccessUrl("/admin/home", true)
                        .failureUrl("/admin/login?error=true")
                        .permitAll())
                .userDetailsService(adminDetailsService).authenticationProvider(adminAuthProvider) 
                .logout(logout -> logout
                        .logoutUrl("/admin/logout")
                        .logoutSuccessUrl("/admin/login?logout=true"));

        return http.build();
    }

    @Bean
    public SecurityFilterChain memberSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/member/**")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/member/login").permitAll()
                        .anyRequest().hasRole("MEMBER"))
                .formLogin(form -> form
                        .loginPage("/member/login")
                        .loginProcessingUrl("/member/login")
                        .defaultSuccessUrl("/member/home", true)
                        .failureUrl("/member/login?error=true")
                        .permitAll())
                .userDetailsService(memberDetailsService).authenticationProvider(memberAuthProvider) 
                .logout(logout -> logout
                        .logoutUrl("/member/logout")
                        .logoutSuccessUrl("/member/login?logout=true"));

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}