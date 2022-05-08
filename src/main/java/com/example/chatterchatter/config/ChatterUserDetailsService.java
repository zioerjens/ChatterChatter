package com.example.chatterchatter.config;

import com.example.chatterchatter.model.UserPrincipal;
import com.example.chatterchatter.model.domain.User;
import com.example.chatterchatter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

@Service
@Transactional
public class ChatterUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByUsername(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found by username: " + username);
        }
        User user = userOptional.get();
        user.setLastLoginDate(new Date());
        userRepository.save(user);

        return new UserPrincipal(user);
    }
}
