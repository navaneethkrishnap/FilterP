package com.NKP.Filter.controller.Auth;

import com.NKP.Filter.dto.LoginDTO;
import com.NKP.Filter.dto.UserRegistrationDTO;
import com.NKP.Filter.model.User;
import com.NKP.Filter.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegistrationDTO dto){

        User userRegister = authService.register(dto);
        return new ResponseEntity<>("Registration Successful, Please login.", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDTO dto){

        String userToken = authService.login(dto);
        return new ResponseEntity<>(userToken,HttpStatus.OK);
    }
}
