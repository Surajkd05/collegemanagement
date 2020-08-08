package com.college.automation.system;

import com.college.automation.system.model.Admin;
import com.college.automation.system.model.Role;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.AdminRepo;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Component
public class ApplicationStartUpRunner implements CommandLineRunner {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        User user = userRepo.findByEmail("admin@admin.com");
        if(null == user){
            Admin admin = new Admin();
            admin.setAccountNonLocked(true);
            admin.setActive(true);
            admin.setAge(29);
            admin.setCredentialsNonExpired(false);
            admin.setEnabled(false);
            admin.setDateOfBirth(new Date());
            admin.setEmail("admin@admin.com");
            admin.setFirstName("Manoj");
            admin.setLastName("Dubey");
            admin.setGender("Male");
            admin.setMobileNo("9836920203");
            String pass = passwordEncoder.encode("Thunder12@");
            admin.setPassword(pass);
            Set<Role> roles = new LinkedHashSet<>();
            roles.add(new Role("ADMIN"));
            admin.setRoles(roles);
            admin.setUsername("admin");
            adminRepo.save(admin);

        }
    }
}
