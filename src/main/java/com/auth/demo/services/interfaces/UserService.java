package com.auth.demo.services.interfaces;

import java.util.UUID;

import org.springframework.http.ResponseEntity;

import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.AuthResponse.AuthResponse;
import com.auth.demo.dto.userDto.LoginDto;
import com.auth.demo.dto.userDto.UserCreateDTO;
import com.auth.demo.dto.userDto.UserResponseDTO;

import jakarta.servlet.http.HttpServletRequest;

public interface UserService {

    ResponseEntity<ApiResponse<UserResponseDTO>> register(UserCreateDTO userCreateDTO);

    ResponseEntity<ApiResponse<AuthResponse>> login(LoginDto loginDTO, HttpServletRequest request);

    ResponseEntity<ApiResponse<UserResponseDTO>> getUserByPublicId(UUID publicId);

    ResponseEntity<ApiResponse<AuthResponse>> refreshAccessToken(String refreshToken);

}