package com.example.apartmentmanagement.config;

import com.example.apartmentmanagement.entity.Role;
import com.example.apartmentmanagement.entity.User;
import com.example.apartmentmanagement.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
public class InitConfig {
    @Autowired
    PasswordEncoder passwordEncoder;
    UserRepository userRepository;
    @Bean
    ApplicationRunner init(UserRepository userRepository) {
        return args -> {
            if (!userRepository.existsByUsername("SuperAssmin")){
                HashSet<Role> roles = new HashSet<>();
                roles.add(Role.ASSMIN);
                User user = User.builder()
                        .username("SuperAssmin")
                        .password(passwordEncoder.encode("assmin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.info("SuperAssmin has been created");
            }
        };
    }
}
