package com.track.activity.service;

import com.track.activity.model.OtpVerification;
import com.track.activity.model.User;
import com.track.activity.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OtpService {

    private static final long OTP_EXPIRY_SECONDS = 300; // 5 minutes

    private final UserRepository userRepository;
    private final SecureRandom secureRandom = new SecureRandom();

    // Temporary DEV storage
    private final Map<String, OtpVerification> otpStore =
            new ConcurrentHashMap<>();

    public OtpService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String sendOtp(String phoneNumber) {

        User user = userRepository.findByPhoneNo(phoneNumber);

        if (user == null) {
            throw new IllegalArgumentException(
                    "User not found with this phone number"
            );
        }

        String otp = String.format(
                "%06d",
                secureRandom.nextInt(1_000_000)
        );

        OtpVerification verification = new OtpVerification();

        verification.setPhoneNumber(phoneNumber);
        verification.setOtp(otp);
        verification.setExpiresAt(
                Instant.now().plusSeconds(OTP_EXPIRY_SECONDS).toEpochMilli()
        );
        verification.setAttempts(0);

        otpStore.put(phoneNumber, verification);

        // ONLY for development/testing
        System.out.println("DEV OTP for " + phoneNumber + " = " + otp);

        return otp;
    }
}