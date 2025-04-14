package com.example.demo.controller;

import com.example.demo.model.User;


import com.example.demo.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginForm(@RequestBody User loginUser) {
        try {
            User user = userRepository.findByUsername(loginUser.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            boolean matches = passwordEncoder.matches(loginUser.getPassword(), user.getPassword());

            if (!matches) {
                return ResponseEntity.status(401).body("Incorrect password or login");
            }

            return ResponseEntity.ok("Login successful! Welcome, " + user.getUsername());
        } catch (Exception e) {
            return ResponseEntity.status(404).body("User not found");
        }
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) {
        System.out.println("Received user: " + user);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setDate(new Date());
        User saved = userRepository.save(user);
        System.out.println("Saved user: " + saved);

        return ResponseEntity.ok(saved);
    }



    @GetMapping("/home")
    public ResponseEntity<String> home() {
        return ResponseEntity.ok("Welcome, authenticated user!");
    }
}