package com.event.manager.EventManger.controller;

import com.event.manager.EventManger.configuration.JwtTokenProvider;
import com.event.manager.EventManger.payload.JWTAuthResponse;
import com.event.manager.EventManger.payload.LoginDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class Logincontroller {


    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider tokenProvider;

    @Autowired
    public Logincontroller(AuthenticationManager authenticationManager, JwtTokenProvider tokenProvider) {
        this.authenticationManager = authenticationManager;
        this.tokenProvider = tokenProvider;
    }

    @PostMapping()
    public ResponseEntity<?> login(@RequestBody LoginDto logindto){
        Authentication authentication = authenticationManager.authenticate(
                new
                        UsernamePasswordAuthenticationToken(logindto.getEmail(),
                        logindto.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // get token form tokenProvider
        String token = tokenProvider.generateToken((UserDetails) authentication.getPrincipal());
        return ResponseEntity.ok(new JWTAuthResponse(token));
    }


}
