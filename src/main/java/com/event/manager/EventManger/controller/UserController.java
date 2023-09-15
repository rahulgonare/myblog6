package com.event.manager.EventManger.controller;

import com.event.manager.EventManger.configuration.JwtTokenProvider;
import com.event.manager.EventManger.payload.JWTAuthResponse;
import com.event.manager.EventManger.payload.LoginDto;
import com.event.manager.EventManger.payload.OtpDto;
import com.event.manager.EventManger.payload.RegisteredUserDTO;
import com.event.manager.EventManger.repository.RegisteredUserRepository;
import com.event.manager.EventManger.repository.TemporaryRepository;
import com.event.manager.EventManger.services.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.FieldError;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/")
public class UserController {

    private final UserServices userServices;
    private final RegisteredUserRepository registeredUserRepository;
    private final TemporaryRepository temporaryRepository;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public UserController(UserServices userServices, RegisteredUserRepository registeredUserRepository, TemporaryRepository temporaryRepository, AuthenticationManager authenticationManager) {
        this.userServices = userServices;
        this.registeredUserRepository = registeredUserRepository;
        this.temporaryRepository = temporaryRepository;
        
        this.authenticationManager = authenticationManager;
    }



    @PostMapping("/registerEvent")
    public ResponseEntity<?> registerEvent(@Valid @RequestBody RegisteredUserDTO userDTO, BindingResult result) {

        if (result.hasErrors()) {
            return ResponseEntity.internalServerError().body(result.getFieldErrors().stream()
                    .map(FieldError::getDefaultMessage)
                    .collect(Collectors.toList()));
        }
        if(temporaryRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.ok(userServices.sendOtp(userDTO.getEmail()));
        }
        if(registeredUserRepository.existsByEmail(userDTO.getEmail())){
            return ResponseEntity.ok(userServices.sendOtp(userDTO.getEmail()));
        }

       return ResponseEntity.ok(userServices.addNewUser(userDTO));

    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOTP(@RequestBody OtpDto otpDto) {

        // Call a service method to verify the OTP
        boolean isOTPValid = userServices.verifyOtp(otpDto);

        if (isOTPValid) {
            return ResponseEntity.ok("OTP is valid, and Event Added");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid OTP or Expired otp regenerate one");
        }
    }



}
