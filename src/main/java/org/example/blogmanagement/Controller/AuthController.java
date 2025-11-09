package org.example.blogmanagement.Controller;

import org.example.blogmanagement.Jwt.JwtUtil;
import org.example.blogmanagement.Models.Role;
import org.example.blogmanagement.Models.User;
import org.example.blogmanagement.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * This controller handles authentication-related operations:
 * - User registration (regular users)
 * - Admin registration (admin accounts)
 * - Login and JWT token generation
 */
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    // Used to authenticate user credentials (username + password)
    @Autowired
    private AuthenticationManager authenticationManager;


    // Repository to interact with the User table in the database
    @Autowired
    private UserRepository userRepository;

    // Used to hash passwords before saving them in the database
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.USER); // Regular users
        userRepository.save(user);
        return "User registered successfully!";
    }

    @PostMapping("/register/admin")
    public String registerAdmin(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.ADMIN); // Admin users
        userRepository.save(user);
        return "Admin registered successfully!";
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");

        // Authenticate user credentials using AuthenticationManager
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );


        // If authentication passes, find the user in the database
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token for this user
        String token = jwtUtil.generateToken(user);

        // Return the JWT token as response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;
    }
}