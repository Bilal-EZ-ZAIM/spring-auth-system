package com.auth.demo.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.AuthResponse.AuthResponse;
import com.auth.demo.dto.userDto.LoginDto;
import com.auth.demo.dto.userDto.UserCreateDTO;
import com.auth.demo.dto.userDto.UserResponseDTO;
import com.auth.demo.exception.AppException;
import com.auth.demo.services.interfaces.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/auth")
public class UserController {

    public final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponseDTO>> register(@RequestBody @Valid UserCreateDTO userCreateDTO) {

        return this.userService.register(userCreateDTO);
    }

    @PostMapping("login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody @Valid LoginDto loginDot,
            HttpServletRequest request) {
        return this.userService.login(loginDot, request);
    }

    @GetMapping("refresh-token")
    public ResponseEntity<ApiResponse<AuthResponse>> refreshAccessToken(
            @CookieValue(name = "refreshToken", required = false) String refreshToken) {

        if (refreshToken == null) {
            throw AppException.unauthorized("Refresh token is missing");
        }

        return this.userService.refreshAccessToken(refreshToken);
    }
}
