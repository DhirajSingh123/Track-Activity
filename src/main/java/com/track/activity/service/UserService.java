package com.track.activity.service;

import com.track.activity.dto.LoginRequest;
import com.track.activity.dto.UserRegistrationRequest;
import com.track.activity.model.User;
import com.track.activity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(UserRegistrationRequest request) {

        User existingPhone =
                userRepository.findByPhoneNo(request.getPhoneNo());

        if (existingPhone != null) {
            throw new IllegalArgumentException(
                    "Phone number already registered"
            );
        }

        User existingEmail =
                userRepository.findByEmailId(request.getEmailId());

        if (existingEmail != null) {
            throw new IllegalArgumentException(
                    "Email ID already registered"
            );
        }

        User user = new User();

        user.setUserId(generateUserId());
        user.setName(request.getName());
        user.setEmailId(request.getEmailId());
        user.setPhoneNo(request.getPhoneNo());
        user.setCreatedAt(Instant.now().toString());

        return userRepository.save(user);
    }

    public User login(LoginRequest request) {

        User user =
                userRepository.findByPhoneNo(request.getPhoneNo());

        if (user == null) {
            throw new IllegalArgumentException(
                    "User not found with this phone number"
            );
        }

        return user;
    }

    private String generateUserId() {

        String shortId = UUID.randomUUID()
                .toString()
                .substring(0, 6)
                .toUpperCase();

        return "USER-" + shortId;
    }
}