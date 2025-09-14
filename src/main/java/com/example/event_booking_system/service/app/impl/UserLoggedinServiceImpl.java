package com.example.event_booking_system.service.app.impl;

import com.example.event_booking_system.repository.managementuser.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserLoggedinServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepository.findByUsername(username.toLowerCase())
                .orElseThrow(() -> new UsernameNotFoundException("User : " + username + " tidak ditemukan"));
        return new UserLoggedInConfig(user);
    }
}
