package org.example.backmobile.Services.Impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.RedisConnectionFailureException;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final Logger logger = LoggerFactory.getLogger(OtpService.class);
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 5; // Changed to 5 minutes instead of 30 days
    private static final String OTP_PREFIX = "OTP:"; // Prefix for Redis keys

    @Autowired
    private StringRedisTemplate redisTemplate;
    private void ensureRedisConnection() {
        int maxRetries = 3;
        int retryCount = 0;
        while (retryCount < maxRetries) {
            try {
                redisTemplate.getConnectionFactory().getConnection();
                return;
            } catch (RedisConnectionFailureException e) {
                retryCount++;
                if (retryCount == maxRetries) {
                    throw e;
                }
                logger.warn("Redis connection attempt {} failed, retrying...", retryCount);
                try {
                    Thread.sleep(1000); // Wait 1 second before retrying
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Interrupted while attempting to connect to Redis", ie);
                }
            }
        }
    }
    public String generateOtp(String telephone) {
        try {
            // Validate telephone
            if (telephone == null || telephone.trim().isEmpty()) {
                throw new IllegalArgumentException("Telephone number cannot be null or empty");
            }

            // Generate 6-digit OTP
            String otp = String.format("%06d", (int) (Math.random() * 1000000));

            // Store in Redis with prefix
            String redisKey = OTP_PREFIX + telephone;
            redisTemplate.opsForValue().set(redisKey, otp, OTP_EXPIRATION_MINUTES, TimeUnit.MINUTES);

            // Verify storage
            String storedOtp = redisTemplate.opsForValue().get(redisKey);
            if (storedOtp == null) {
                throw new RuntimeException("Failed to store OTP in Redis");
            }

            logger.info("OTP generated and stored for telephone: {}", telephone);
            sendOtp(telephone, otp);

            return otp;

        } catch (RedisConnectionFailureException e) {
            logger.error("Redis connection failed while generating OTP", e);
            throw new RuntimeException("Failed to generate OTP due to cache service error", e);
        } catch (Exception e) {
            logger.error("Error generating OTP for telephone: " + telephone, e);
            throw new RuntimeException("Failed to generate OTP", e);
        }
    }

    public boolean verifyOtp(String telephone, String otp) {
        try {
            // Validate inputs
            if (telephone == null || telephone.trim().isEmpty() || otp == null || otp.trim().isEmpty()) {
                logger.warn("Invalid verification attempt with null/empty telephone or OTP");
                return false;
            }

            // Get stored OTP
            String redisKey = OTP_PREFIX + telephone;
            String storedOtp = redisTemplate.opsForValue().get(redisKey);

            logger.info("Verifying OTP for telephone: {}", telephone);

            // If verification successful, delete the OTP
            if (otp.equals(storedOtp)) {
                redisTemplate.delete(redisKey);
                logger.info("OTP verified successfully for telephone: {}", telephone);
                return true;
            }

            logger.warn("OTP verification failed for telephone: {}", telephone);
            return false;

        } catch (RedisConnectionFailureException e) {
            logger.error("Redis connection failed while verifying OTP", e);
            throw new RuntimeException("Failed to verify OTP due to cache service error", e);
        } catch (Exception e) {
            logger.error("Error verifying OTP for telephone: " + telephone, e);
            throw new RuntimeException("Failed to verify OTP", e);
        }
    }

    private void sendOtp(String telephone, String otp) {
        try {
            // Your SMS sending logic here
            logger.info("OTP {} sent to telephone {}", otp, telephone);
        } catch (Exception e) {
            logger.error("Failed to send OTP to telephone: " + telephone, e);
            // Depending on your requirements, you might want to throw this exception
            // throw new RuntimeException("Failed to send OTP", e);
        }
    }

    // Optional: Method to check if an OTP exists
    public boolean hasExistingOtp(String telephone) {
        try {
            String redisKey = OTP_PREFIX + telephone;
            return Boolean.TRUE.equals(redisTemplate.hasKey(redisKey));
        } catch (Exception e) {
            logger.error("Error checking existing OTP for telephone: " + telephone, e);
            return false;
        }
    }

    // Optional: Method to delete an OTP
    public void deleteOtp(String telephone) {
        try {
            String redisKey = OTP_PREFIX + telephone;
            redisTemplate.delete(redisKey);
            logger.info("OTP deleted for telephone: {}", telephone);
        } catch (Exception e) {
            logger.error("Error deleting OTP for telephone: " + telephone, e);
        }
    }
}