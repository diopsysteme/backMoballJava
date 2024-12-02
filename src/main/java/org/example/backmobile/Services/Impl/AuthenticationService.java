package org.example.backmobile.Services.Impl;

import org.example.backmobile.Datas.Entity.User;
//import org.example.backmobile.Datas.Entity.UserEntity;
import org.example.backmobile.Datas.Repository.UserRepository;
import org.example.backmobile.Web.Dtos.Mapper.LoginMapper;
import org.example.backmobile.Web.Dtos.Mapper.UserMapper;
import org.example.backmobile.Web.Dtos.Request.LoginUserDto;
import org.example.backmobile.Web.Dtos.Request.UserRequestDto;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import prog.dependancy.Exceptions.ResourceNotFoundException;
import prog.dependancy.Services.AuthenticationIService;

@Service
public class AuthenticationService implements AuthenticationIService<User, UserRequestDto, LoginUserDto> {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LoginMapper loginMapper;
    private final AuthenticationManager authenticationManager;

    public AuthenticationService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder,
            UserMapper userMapper,
            LoginMapper loginMapper
    ) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.loginMapper = loginMapper;
    }



    // Assuming you have a password encoding method
    private String encodePassword(String password) {
        return passwordEncoder.encode(password); // Example using BCryptPasswordEncoder
    }


    @Override
    public User signup(UserRequestDto input) {
        User user = userMapper.toEntity(input);

        user.setDeleted(false);
        return  userRepository.save(user);
    }

    @Override
    public User authenticate(LoginUserDto input) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        input.getTelephone(),
                        input.getPassword()
                )
        );
        return userRepository.findByTelephone(input.getTelephone()).orElseThrow();
    }
}
