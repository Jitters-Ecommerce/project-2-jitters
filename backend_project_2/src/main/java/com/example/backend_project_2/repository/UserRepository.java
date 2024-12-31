package com.example.repository;

import com.example.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

	// Method to find user in repository who has a particular email

    public User findUserByEmail(String email);

}