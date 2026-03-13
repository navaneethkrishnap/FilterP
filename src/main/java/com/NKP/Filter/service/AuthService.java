package com.NKP.Filter.service;

import com.NKP.Filter.dto.LoginDTO;
import com.NKP.Filter.dto.UserRegistrationDTO;
import com.NKP.Filter.model.User;
import com.NKP.Filter.repo.UserRepository;
import com.NKP.Filter.service.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public User register(UserRegistrationDTO register){

        // check whether same username exists if yes, returns exception
        if(userRepository.existsByUsername(register.getUsername())){
            throw new RuntimeException("Username already exists");
        }

        User newUserRegistration = User.builder()
                .name(register.getName())
                .username(register.getUsername())
                .userPassword(passwordEncoder.encode(register.getPassword()))
                .dob(register.getDob())
                .email(register.getEmail())
                .build();

        return userRepository.save(newUserRegistration);

    }

    public String login(LoginDTO dto){
        User user = userRepository.
                findByUsername(dto.getUsername())
                .orElseThrow(
                ()-> new RuntimeException("Incorrect username or password")
        );

        if(!passwordEncoder.matches(dto.getPassword(), user.getUserPassword())){
            throw new RuntimeException("Incorrect username or password");
        }

        String token = jwtService.generateToken(user.getUsername());
        return token;
    }


}
