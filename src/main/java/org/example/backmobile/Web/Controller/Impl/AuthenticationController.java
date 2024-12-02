package org.example.backmobile.Web.Controller.Impl;

import org.example.backmobile.Datas.Entity.User;
import org.example.backmobile.Services.Impl.AuthentCodeotp;
import org.example.backmobile.Services.Interfaces.AuthenticationIService;
import org.example.backmobile.Services.Interfaces.JwtServiceInterface;
import org.example.backmobile.Web.Dtos.Mapper.LoginMapper;
import org.example.backmobile.Web.Dtos.Request.LoginUserDto;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;
import org.example.backmobile.Web.Dtos.Response.LoginResponse;
import org.example.backmobile.Web.Dtos.Response.ResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prog.dependancy.Web.DTO.ApiResponse;

import java.util.Map;
import java.util.Optional;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final JwtServiceInterface jwtService;
    private final LoginMapper loginMapper;
    private final AuthentCodeotp authentCodeotp;

    @Autowired
    public AuthenticationController(JwtServiceInterface jwtService,
                                    LoginMapper loginMapper,
                                    AuthentCodeotp authentCodeotp) {
        this.jwtService = jwtService;
        this.loginMapper = loginMapper;
        this.authentCodeotp = authentCodeotp;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<User> register(@RequestBody UserRequestDto registerUserDto) {
//         authenticationService.signup(registerUserDto);
//
//    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
//        User authenticatedUser = authenticationService.authenticate(loginUserDto);
//        String jwtToken = jwtService.generateToken(authenticatedUser);
//        LoginResponse loginResponse = loginMapper.toDto(authenticatedUser);
//        loginResponse.setToken(jwtToken);
//        return ResponseEntity.ok(loginResponse);
//    }

    @PostMapping("/authenticate/otp")
    public ResponseEntity<ApiResponse<String>> authenticateWithOtp(@RequestBody Map<String, String> payload) {
        String telephone = payload.get("telephone");
        if(telephone == null || telephone.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return authentCodeotp.authenticate(telephone)
                .map(response -> new ResponseEntity<>(new ApiResponse<>(response, "Authentication successful"), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiResponse<>(null, "Authentication failed"), HttpStatus.BAD_REQUEST));
    }
    @PostMapping("/verify/otp")
    public ResponseEntity<ApiResponse<ResponseDto>> verifyOtp(@RequestBody Map<String, String> payload) {
        String telephone = payload.get("telephone");
        String otp = payload.get("otp");

        if (telephone == null || telephone.isEmpty() || otp == null || otp.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "Missing required fields: telephone or otp"));
        }

        Optional<Object> result = authentCodeotp.verifyOtp(telephone, otp);

        if (result.isPresent()) {
            Object response = result.get();

            // If the response is an instance of ResponseDto, return it in the response
            if (response instanceof ResponseDto) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new ApiResponse<>((ResponseDto) response, "OTP verification successful"));
            } else {
                // In case of error message such as "User does not exist"
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ApiResponse<>(null, (String) response));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "OTP verification failed"));
        }
    }

    @PostMapping("/verify/pin")
    public ResponseEntity<ApiResponse<String>> verifyPin(@RequestBody Map<String, String> payload) {
        String pin = payload.get("pin");

        if (pin == null || pin.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(null, "Missing required field: pin"));
        }

        return authentCodeotp.verifyPin(pin)
                .map(response -> new ResponseEntity<>(new ApiResponse<>(response, "PIN verification successful"), HttpStatus.OK))
                .orElse(new ResponseEntity<>(new ApiResponse<>(null, "PIN verification failed"), HttpStatus.BAD_REQUEST));
    }





}
