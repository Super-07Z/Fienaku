package bancofie.com.bo.fienaku.settings;

import bancofie.com.bo.fienaku.service.userDetailsServiceImpl;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity

public class securityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    userDetailsServiceImpl userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                
                .antMatchers(HttpMethod.POST, "/user/register").permitAll()
                
                .antMatchers(HttpMethod.POST, "/user/uploadImage").hasAnyRole("USER", "MANAGER", "ADMIN")

                .antMatchers(HttpMethod.POST, "/user/update").hasAnyRole("USER", "MANAGER", "ADMIN")
                
                .antMatchers(HttpMethod.POST, "/user/delete/{id}").hasAnyRole("USER", "MANAGER", "ADMIN")
                
                .antMatchers(HttpMethod.POST, "/user/{id}").hasAnyRole("MANAGER", "ADMIN")
                
                .antMatchers(HttpMethod.POST, "/user/all").hasRole("ADMIN")
                
                
                .antMatchers(HttpMethod.POST, "/user/{id]").hasRole("ADMIN")
                
                .antMatchers(HttpMethod.POST, "/fienaku/**").hasAnyRole("ADMIN", "MANAGER")
                .antMatchers(HttpMethod.POST, "/payment/**").hasRole("MANAGER")
                .antMatchers(HttpMethod.POST, "/charge/**").hasRole("MANAGER")

                .antMatchers(HttpMethod.POST, "/fienaku/create").hasRole("USER")
                .antMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**", "/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .httpBasic()
            .and()
                .formLogin()
                    .permitAll()
            .and()
                .logout()
                    .permitAll();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
