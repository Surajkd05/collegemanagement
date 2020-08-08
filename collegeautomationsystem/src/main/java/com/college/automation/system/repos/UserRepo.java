package com.college.automation.system.repos;

import com.college.automation.system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);

    User findByEmail(String email);

    User findByUserId(Long userId);

    //Query to find UserId
    @Query(value = "SELECT userId FROM User WHERE username=:username")
    Long findUserId(@Param("username") String username);

    //To update username
    @Query(value = "UPDATE user SET user.username=:userName where user.user_id=:userId",nativeQuery = true)
    void updateUserUsername(@Param("userId") Long userId, @Param("userName") String userName);

    //To update firstname
    @Query(value = "UPDATE user SET user.first_name=:firstName where user.user_id=:userId",nativeQuery = true)
    void updateUserFirstName(@Param("userId") Long userId, @Param("firstName") String firstName);

    //To update lastname
    @Query(value = "UPDATE user SET user.last_name=:lastName where user.user_id=:userId",nativeQuery = true)
    void updateUserLastName(@Param("userId") Long userId, @Param("lastName") String lastName);

    //To update email
    @Query(value = "UPDATE user SET user.email=:email where user.user_id=:userId",nativeQuery = true)
    void updateUserEmail(@Param("userId") Long userId, @Param("email") String email);

    //To update password
    @Query(value = "UPDATE user SET user.password=:password where user.user_id=:userId",nativeQuery = true)
    void updateUserPassword(@Param("userId") Long userId, @Param("password") String password);

}
