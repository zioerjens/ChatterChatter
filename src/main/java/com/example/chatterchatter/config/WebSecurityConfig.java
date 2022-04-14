package com.example.chatterchatter.config;

import com.example.chatterchatter.security.ChatterUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ChatterUserDetailsService userDetailsService;

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        return userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/api/").permitAll()
            //.antMatchers("/contacts/add", "/contacts/{id}/edit", "/contacts/{id}/delete").hasRole("ADMIN")
            .anyRequest().authenticated();
    }
}
