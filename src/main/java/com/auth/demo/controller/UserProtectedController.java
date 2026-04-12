package com.auth.demo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.userDto.UserResponseDTO;
import com.auth.demo.services.interfaces.UserService;

@RestController
@RequestMapping("api/v1/users")
public class UserProtectedController {

    private final UserService userService;

    public UserProtectedController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("me")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getMyUser(Authentication authentication) {

        String userId = authentication.getName();
        return userService.getUserByPublicId(UUID.fromString(userId));
    }

    @GetMapping("test")
    public String test(Authentication authentication) {
        return "Hello, " + authentication.getName() + "! This is a protected endpoint.";
    }
}