package com.example.PDPMobileGame.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;



@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomRequestFilter customRequestFilter;

    @Autowired
    private CheckIsExpiredTokenFilter checkIsExpiredTokenFilter;

    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .addFilterBefore(checkIsExpiredTokenFilter, BasicAuthenticationFilter.class)
            .addFilterBefore(customRequestFilter, BasicAuthenticationFilter.class);
//
//         інші налаштування безпеки
    }
}
