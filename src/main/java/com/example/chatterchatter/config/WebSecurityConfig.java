package com.example.chatterchatter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        var builder = User.withDefaultPasswordEncoder();

        var user = builder.username("zimmermaj").password("user").roles("").build();
        var admin = builder.username("admin").password("admin").roles("ADMIN").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/", "/api/**").permitAll()
            //.antMatchers("/contacts/add", "/contacts/{id}/edit", "/contacts/{id}/delete").hasRole("ADMIN")
            .anyRequest().authenticated();
    }
}
