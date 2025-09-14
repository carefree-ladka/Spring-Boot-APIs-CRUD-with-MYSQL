package com.example.mysql_crud.security;

import com.example.mysql_crud.entity.User;
import com.example.mysql_crud.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Load User entity from DB
        return userRepository.findByUsername(username)
                       .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + username));
    }
}
