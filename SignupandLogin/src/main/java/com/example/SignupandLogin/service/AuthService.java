package com.example.SignupandLogin.service;



import com.example.SignupandLogin.dto.*;
import com.example.SignupandLogin.entity.User;
import com.example.SignupandLogin.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;

    public String signup(SignupRequest request) {
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            return "User already exists";
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(request.getPassword()) // No encryption here!
                .build();

        userRepo.save(user);
        return "Signup successful";
    }

    public String login(LoginRequest request) {
        return userRepo.findByEmail(request.getEmail())
                .filter(user -> user.getPassword().equals(request.getPassword()))
                .map(user -> "Login successful")
                .orElse("Invalid email or password");
    }
}
