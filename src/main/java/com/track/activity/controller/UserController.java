package com.track.activity.controller;

import com.track.activity.dto.LoginRequest;
import com.track.activity.dto.UserRegistrationRequest;
import com.track.activity.model.User;
import com.track.activity.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(
            @RequestBody UserRegistrationRequest request) {

        return ResponseEntity.ok(
                userService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(
            @RequestBody LoginRequest request) {

        return ResponseEntity.ok(
                userService.login(request)
        );
    }
}