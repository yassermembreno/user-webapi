package dev.yrmg.userwebapi.userwebapi.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import dev.yrmg.userwebapi.userwebapi.application.interfaces.IUserDetailService;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtSecurityConfiguration extends WebSecurityConfigurerAdapter{
    @Autowired
    private IUserDetailService detailService;

    protected void configure(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests().antMatchers("/api/login").permitAll()
            .antMatchers("/api/h2-console/**").permitAll().anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();

        // No session will be created or used by spring security
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(new JwtFilter(detailService), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2Y);
    }

    @Bean
    public AuthenticationManager gAuthenticationManager() throws Exception{
        return super.authenticationManagerBean();
    }
}
