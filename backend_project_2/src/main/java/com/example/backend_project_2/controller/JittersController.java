package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.User;
import com.example.repository.UserRepository;
import com.example.service.UserService;


@RestController
public class JittersController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user){
        User existingUser = userService.getUserByEmail(user.getEmail());
        
        if (existingUser != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        User registeredAccount = userService.saveUser(user);

        return new ResponseEntity<>(registeredAccount, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> loginUser(@RequestBody User user){
        User loggedInAccount = userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedInAccount != null){
            return new ResponseEntity<>(loggedInAccount, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
