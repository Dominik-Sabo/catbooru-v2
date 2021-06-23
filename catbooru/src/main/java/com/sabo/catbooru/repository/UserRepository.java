package com.sabo.catbooru.repository;

import com.sabo.catbooru.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
    
}
