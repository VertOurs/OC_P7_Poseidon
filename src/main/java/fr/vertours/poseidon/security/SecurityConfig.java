package fr.vertours.poseidon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MyUserDetailsService service;
    private final LoginSuccessHandler successHandler;

    public SecurityConfig(MyUserDetailsService service, LoginSuccessHandler successHandler) {
        this.service = service;
        this.successHandler = successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        String[] ressources = new String[] {
                "/", "/css/**", "/img/**", "/app/**",
        };
        String[] userEP = new String[] {
                "/trade/**", "/ruleName/**", "/rating/**",
                "/curvePoint/**", "/bidList/**"
        };
        String[] adminEP = new String[] {
                "/user/**", "/admin/**"
        };

        http
                .authorizeRequests()
                    .antMatchers(adminEP).hasAuthority("ADMIN")
                    .antMatchers(userEP).hasAnyAuthority("USER","ADMIN" )
                    .antMatchers(ressources).permitAll()
                    .anyRequest().authenticated()
                .and()
                    .formLogin()
                    .loginPage("/app/login")
                    .usernameParameter("username")
                    .passwordParameter("password")
                    .successHandler(successHandler).permitAll()
                .and()
                    .logout()
                    .logoutUrl("/app-logout")
                    .logoutSuccessUrl("/")
                    .and()
                    .exceptionHandling()
                    .accessDeniedPage("/app/error")
                ;

    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }



}
