package com.example.chatterchatter.security;

import com.example.chatterchatter.model.UserPrincipal;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ChatterUserDetailsService/* implements UserDetailsService*/ {

    @Autowired
    private UserRepository userRepository;

    //@Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }
        return new UserPrincipal(user.get());
    }
}
