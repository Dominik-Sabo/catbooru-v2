package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

    @Modifying
    @Query("update User user set user.isAdmin = true where user.username = ?1")
    void makeAdmin(String username);
    
}
