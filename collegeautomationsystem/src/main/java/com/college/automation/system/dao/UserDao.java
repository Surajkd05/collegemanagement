package com.college.automation.system.dao;

import com.college.automation.system.exceptions.NotFoundException;
import com.college.automation.system.exceptions.BadRequestException;
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

        List<GrantedAuthorityImpl> grantedAuthorityImpl = new ArrayList<>();

        if(user.isActive()) {
            if (username != null) {
                for (Role auth : user.getRoles()) {
                    grantedAuthorityImpl.add(new GrantedAuthorityImpl(auth.getRole()));
                }
                return new AppUser(user.getUserId(),user.getEmail(), user.getPassword(),
                            grantedAuthorityImpl, !user.isEnabled(), !user.isCredentialsNonExpired(), user.isAccountNonLocked());
            } else {
                throw new NotFoundException("User not found");
            }
        }else {
            throw new BadRequestException("Account is not activated. Check your mail for account activation link.If not found in mail inbox, then check spams or promotions inside your mail.");
        }
    }
}
