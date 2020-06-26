package com.college.automation.system.dao;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.model.AppUser;
import com.college.automation.system.model.GrantedAuthorityImpl;
import com.college.automation.system.model.Role;
import com.college.automation.system.model.User;
import com.college.automation.system.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class UserDao {
    @Autowired
    private UserRepo userRepo;

    public AppUser loadUserByUsername(String username) {
        User user = userRepo.findByEmail(username);
        System.out.println(user.getPassword());

        List<GrantedAuthorityImpl> grantedAuthorityImpl = new ArrayList<>();

        if(user.isActive()) {
            if (username != null) {
                for (Role auth : user.getRoles()) {
                    grantedAuthorityImpl.add(new GrantedAuthorityImpl(auth.getRole()));
                }

                System.out.println("Username in Dao : "+username);
                return new AppUser(user.getUserId(),user.getEmail(), user.getPassword(),
                            grantedAuthorityImpl, !user.isEnabled(), !user.isCredentialsNonExpired(), user.isAccountNonLocked());
            } else {
                throw new NotFoundException("User not found");
            }
        }else {
            throw new RuntimeException("Account is not activated.");
        }
    }
}
