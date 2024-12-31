package com.example.service;

import com.example.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.repository.UserRepository;


@Service
public class UserService {
    @Autowired
	private UserRepository userRepository;

    /**
     * Retrieves a specific user by email.
     * @param email
     * @return user
     */
    public User getUserByEmail(String email){
        return userRepository.findUserByEmail(email);
    }
	
    /**
     * Autheticates user by email and password.
     * @param email
     * @param password
     * @return user
     */
	public User loginUser(String email, String password) {
		User user = getUserByEmail(email);

        if(user != null && user.getPassword().equals(password)){
            return user;
        }

        return null;
	}

    /**
     * Saves user to the repository.
     * @param user
     * @return saved user
     */
    public User saveUser(User user) {
        return userRepository.save(user);
    }

}
