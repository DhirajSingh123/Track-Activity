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

    public User verifyOtp(String phoneNumber, String otp) {

        OtpVerification verification = otpStore.get(phoneNumber);

        // OTP requested hi nahi hua
        if (verification == null) {
            throw new IllegalArgumentException(
                    "OTP not found. Please request a new OTP."
            );
        }

        // OTP expired
        if (Instant.now().toEpochMilli() > verification.getExpiresAt()) {
            otpStore.remove(phoneNumber);

            throw new IllegalArgumentException(
                    "OTP expired. Please request a new OTP."
            );
        }

        // Wrong OTP
        if (!verification.getOtp().equals(otp)) {

            verification.setAttempts(
                    verification.getAttempts() + 1
            );

            if (verification.getAttempts() >= 3) {
                otpStore.remove(phoneNumber);

                throw new IllegalArgumentException(
                        "Too many incorrect attempts. Please request a new OTP."
                );
            }

            throw new IllegalArgumentException("Invalid OTP");
        }

        // Correct OTP - remove it so it cannot be reused
        otpStore.remove(phoneNumber);

        User user = userRepository.findByPhoneNo(phoneNumber);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        return user;
    }
}