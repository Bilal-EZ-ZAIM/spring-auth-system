package com.auth.demo.services.impl;

import java.util.UUID;

import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth.demo.Entity.User;
import com.auth.demo.apiResponces.ApiResponse;
import com.auth.demo.dto.AuthResponse.AuthResponse;
import com.auth.demo.dto.userDto.*;
import com.auth.demo.exception.AppException;
import com.auth.demo.repository.UserRepository;
import com.auth.demo.security.JwtService;
import com.auth.demo.services.interfaces.UserService;
import com.github.f4b6a3.uuid.UuidCreator;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class UserServicesImpl implements UserService {

        private final UserRepository userRepository;
        private final PasswordEncoder passwordEncoder;
        private final JwtService jwtService;
        private final UserSessionsServiceImpl userSessionsService;

        public UserServicesImpl(UserRepository userRepository,
                        PasswordEncoder passwordEncoder,
                        JwtService jwtService, UserSessionsServiceImpl userSessionsService) {
                this.userRepository = userRepository;
                this.passwordEncoder = passwordEncoder;
                this.jwtService = jwtService;
                this.userSessionsService = userSessionsService;
        }

        // REGISTER
        public ResponseEntity<ApiResponse<UserResponseDTO>> register(UserCreateDTO dto) {

                if (userRepository.existsByEmail(dto.email())) {
                        throw AppException.badRequest("Email already in use", "email");
                }

                User user = new User();
                user.setFirstname(dto.firstname());
                user.setLastname(dto.lastname());
                user.setEmail(dto.email());
                user.setPassword(passwordEncoder.encode(dto.password()));

                userRepository.save(user);

                UserResponseDTO response = new UserResponseDTO(
                                user.getPublicId(),
                                user.getFirstname(),
                                user.getLastname(),
                                user.getEmail());

                return ResponseEntity.status(HttpStatus.CREATED)
                                .body(new ApiResponse<>(response, "User created", 201));
        }

        // LOGIN
        public ResponseEntity<ApiResponse<AuthResponse>> login(LoginDto dto, HttpServletRequest request) {

                User user = userRepository.findByEmail(dto.email())
                                .orElseThrow(() -> AppException.badRequest("Invalid credentials"));

                if (!passwordEncoder.matches(dto.password(), user.getPassword())) {
                        throw AppException.badRequest("Invalid credentials");
                }

                String accessToken = jwtService.accessToken(user.getEmail(), user.getPublicId());

                String refreshToken = UuidCreator.getTimeOrderedEpoch().toString();
                Boolean sessionCreated = userSessionsService.createSession(user, request, refreshToken);

                if (!sessionCreated) {
                        throw AppException.internalServerError("Failed to create user session");
                }

                ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(7 * 24 * 60 * 60)
                                .sameSite("Strict")
                                .build();

                AuthResponse auth = new AuthResponse(accessToken);

                return ResponseEntity.ok()
                                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                                .body(new ApiResponse<>(auth, "Login successful", 200));
        }

        // REFRESH TOKEN
        public ResponseEntity<ApiResponse<AuthResponse>> refreshAccessToken(String refreshToken) {

                if (!userSessionsService.isSessionValid(refreshToken)) {
                        throw AppException.unauthorized("Invalid or expired session");
                }

                User user = userSessionsService.getUserByRefreshToken(refreshToken);
                String newAccessToken = jwtService.accessToken(user.getEmail(), user.getPublicId());

                AuthResponse auth = new AuthResponse(newAccessToken);

                return ResponseEntity.ok(new ApiResponse<>(auth, "Access token refreshed", 200));
        }

        // GET USER
        public ResponseEntity<ApiResponse<UserResponseDTO>> getUserByPublicId(UUID publicId) {

                User user = userRepository.findByPublicId(publicId)
                                .orElseThrow(() -> AppException.notFound("User not found"));

                UserResponseDTO dto = new UserResponseDTO(
                                user.getPublicId(),
                                user.getFirstname(),
                                user.getLastname(),
                                user.getEmail());

                return ResponseEntity.ok(
                                new ApiResponse<>(dto, "User found", 200));
        }
}