package com.track.activity.controller;

import com.track.activity.dto.AuthResponse;
import com.track.activity.dto.SendOtpRequest;
import com.track.activity.dto.VerifyOtpRequest;
import com.track.activity.model.User;
import com.track.activity.security.JwtService;
import com.track.activity.service.OtpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OtpService otpService;
    @Autowired
    private JwtService jwtService;

    public AuthController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(
            @RequestBody SendOtpRequest request) {

        String otp = otpService.sendOtp(request.getPhoneNumber());

        return ResponseEntity.ok(
                Map.of(
                        "message", "OTP generated successfully",
                        "otp", otp
                )
        );
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<AuthResponse> verifyOtp(
            @RequestBody VerifyOtpRequest request) {

        System.out.println("========== NEW JWT VERIFY OTP CONTROLLER ==========");

        User user = otpService.verifyOtp(
                request.getPhoneNumber(),
                request.getOtp()
        );

        String token = jwtService.generateToken(user);

        System.out.println("JWT GENERATED: " + token.substring(0, 15) + "...");

        return ResponseEntity.ok(
                new AuthResponse(token, user)
        );
    }

}