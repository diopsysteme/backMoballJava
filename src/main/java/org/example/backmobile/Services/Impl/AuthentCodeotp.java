package org.example.backmobile.Services.Impl;

import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Datas.Repository.UserRepository;
import org.example.backmobile.Services.Interfaces.EmailService;
import org.example.backmobile.Services.Interfaces.JwtServiceInterface;
import org.example.backmobile.Web.Dtos.Response.ResponseDto;
import org.example.backmobile.Web.Dtos.Response.UserResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;
@Service
public class AuthentCodeotp {
    @Autowired
    EmailService emailService;
    @Autowired
    private OtpService otpService;
   final  private UserRepository userRepository;
  final  private JwtServiceInterface jwtService;
    public AuthentCodeotp(UserRepository userRepository,
                          JwtServiceInterface jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }
    public Optional<String> authenticate(String telephone) {
        Optional<User> user = Optional.of(userRepository.findByTelephone(telephone).orElseThrow());
        System.out.println(user.toString());
        System.out.println("ddddddd");
        if (user==null) {
            return Optional.of("User exists");
        }
        String otp = otpService.generateOtp(telephone);

        String mail = user.get().getMail();
        String message = "Ne partagez pas ce code votre code otp est : " + otp;
        emailService.sendEmail(mail, message);
        return Optional.of("success");
    }


    public Optional<Object> verifyOtp(String telephone, String otpInput) {
        boolean isOtpValid = otpService.verifyOtp(telephone, otpInput);

        if (isOtpValid) {
            Optional<User> userEntity = userRepository.findByTelephone(telephone);
            if (userEntity.isEmpty()) {
                return Optional.of("User does not exist");
            }

            User user = userEntity.get();

            // Generate the token
            String longDurationToken = jwtService.generateToken((UserDetails) user);

            // Map User to UserResponseDto
            UserResponseDto userResponseDto = new UserResponseDto();
            userResponseDto.setId(user.getId());
            userResponseDto.setNom(user.getNom());
            userResponseDto.setPrenom(user.getPrenom());
            userResponseDto.setMail(user.getMail());
            userResponseDto.setType(user.getType());
            userResponseDto.setTelephone(user.getTelephone());
            userResponseDto.setSolde(user.getSolde());
            userResponseDto.setQr(user.getQr());

            // Create a response object with both token and user data
            ResponseDto response = new ResponseDto();
            response.setToken(longDurationToken);
            response.setUser(userResponseDto);

            return Optional.of(response);
        } else {
            throw new IllegalArgumentException("Invalid OTP");
        }
    }

    public Optional<String> verifyPin(String pinInput) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String telephone = userDetails.getUsername();

        Optional<User> user = userRepository.findByTelephone(telephone);

        if (user.isEmpty()) {
            throw new IllegalArgumentException("User does not exist");
        }

        if (user.get().getCodeSecret().equals(pinInput)) {
            long expirationTime = Duration.ofMinutes(30).toMillis(); // 1 hour in milliseconds
            String shortDurationToken = jwtService.generateSimpleToken(telephone, expirationTime);

            return Optional.of(shortDurationToken);
        } else {
            throw new IllegalArgumentException("Pin invalid");
        }
    }

}
