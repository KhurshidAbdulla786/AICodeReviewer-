package com.aicode.reviewer.security;

import com.aicode.reviewer.entity.User;
import com.aicode.reviewer.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom UserDetailsService that loads user data from the database
 * for Spring Security authentication.
 *
 * @author AI Code Reviewer Team
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userIdStr) throws UsernameNotFoundException {
        try {
            Long userId = Long.parseLong(userIdStr);
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with id: " + userId));
            return CustomUserDetails.from(user);
        } catch (NumberFormatException ex) {
            // If not a number, try username
            User user = userRepository.findByUsername(userIdStr)
                    .orElseThrow(() -> new UsernameNotFoundException(
                            "User not found with username: " + userIdStr));
            return CustomUserDetails.from(user);
        }
    }
}