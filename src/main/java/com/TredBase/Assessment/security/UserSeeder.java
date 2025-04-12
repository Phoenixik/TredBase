package com.TredBase.Assessment.security;

import com.TredBase.Assessment.model.AppUser;
import com.TredBase.Assessment.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UserSeeder implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (Objects.isNull(userRepository.findByUsername("admin"))) {
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRole("ROLE_ADMIN");
            userRepository.save(admin);
        }
        if (Objects.isNull(userRepository.findByUsername("support"))) {
            AppUser support = new AppUser();
            support.setUsername("Support");
            support.setPassword(passwordEncoder.encode("admin123"));
            support.setRole("ROLE_SUPPORT");
            userRepository.save(support);
        }
    }
}
