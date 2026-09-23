package com.track.activity.controller;

import com.track.activity.service.OtpService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final OtpService otpService;

    public AuthController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/send-otp")
    public ResponseEntity<Map<String, String>> sendOtp(
            @RequestBody Map<String, String> request) {

        String phoneNumber = request.get("phoneNumber");

        String otp = otpService.sendOtp(phoneNumber);

        // DEV only - later OTP will be sent through SMS
        return ResponseEntity.ok(
                Map.of(
                        "message", "OTP generated successfully",
                        "otp", otp
                )
        );
    }
}