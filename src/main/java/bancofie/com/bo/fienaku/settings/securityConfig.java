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
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
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
            .antMatchers(HttpMethod.POST, "/user").hasRole("USER")
            .antMatchers("/fienaku").permitAll() 
            .antMatchers("/admin").hasRole("ADMIN") 
            .antMatchers("/manager").hasRole("MANAGER") 
            .antMatchers("/swagger-ui.html").permitAll() 
            .anyRequest().permitAll() 
            .and().httpBasic() 
            .and().formLogin()
            .permitAll()
            .and().logout()
            .permitAll();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
