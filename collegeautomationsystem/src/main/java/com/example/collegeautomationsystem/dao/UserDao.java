package com.example.collegeautomationsystem.dao;

import com.example.collegeautomationsystem.exception.UserAccountLockedExcetion;
import com.example.collegeautomationsystem.exception.UserNotFoundException;
import com.example.collegeautomationsystem.model.AppUser;
import com.example.collegeautomationsystem.model.GrantedAuthorityImpl;
import com.example.collegeautomationsystem.model.Role;
import com.example.collegeautomationsystem.model.User;
import com.example.collegeautomationsystem.repos.UserAttemptRepo;
import com.example.collegeautomationsystem.repos.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private UserRepository userRepository;

    public AppUser loadUserByUsername(String username) {
        User user = userRepository.findByEmail(username);
        System.out.println(user.getPassword());

        List<GrantedAuthorityImpl> grantedAuthorityImpl = new ArrayList<>();

        if(user.isActive()) {
            if (username != null) {
                for (Role auth : user.getRoles()) {
                    grantedAuthorityImpl.add(new GrantedAuthorityImpl(auth.getRole()));
                }

                System.out.println("Username in Dao : "+username);
                return new AppUser(user.getEmail(), user.getPassword(),
                            grantedAuthorityImpl, !user.isEnabled(), !user.isCredentialsNonExpired(), user.isAccountNonLocked());
            } else {
                throw new UserNotFoundException("User not found");
            }
        }else {
            throw new RuntimeException("Account is not activated.");
        }
    }
}
