package com.naviroq.staffhub.identity.security;

import com.naviroq.staffhub.identity.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.debug("Attempting to load user by email: {}", email);

        return userRepository.findByEmail(email)
                .map(CustomUserDetails::new) // 👈 Convert User -> CustomUserDetails
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }
}