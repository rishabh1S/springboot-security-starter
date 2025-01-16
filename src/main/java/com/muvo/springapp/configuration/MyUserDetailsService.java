package com.muvo.springapp.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.muvo.springapp.model.User;
import com.muvo.springapp.repository.UserRepo;

@Component
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;

    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        logger.debug("Entering in loadUserByUsername method.....");
        User user = userRepo.findByEmail(email).orElseThrow(() -> {
            logger.error("Email not found: " + email);
            return new UsernameNotFoundException("Invalid Email");
        });
        logger.info("User Authenticated successfully...!!!!!");
        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
