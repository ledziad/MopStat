package com.mopstat.mopstat.controller;

import com.mopstat.mopstat.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> creds) {
        // uwierzytelnienie
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(creds.get("username"), creds.get("password"))
        );
        // generujemy token
        String token = jwtUtil.generateToken(creds.get("username"));
        return Map.of("token", token);
    }
}
