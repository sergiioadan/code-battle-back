package com.sergioadan.game.application.service;

import com.sergioadan.game.domain.model.Users;
import com.sergioadan.game.domain.repository.UserRepository;
import com.sergioadan.game.infraestructure.config.JwtAuthFilter;
import com.sergioadan.game.infraestructure.config.SecurityConfig;
import com.sergioadan.game.infraestructure.rest.dto.AuthRequest;
import com.sergioadan.game.infraestructure.rest.dto.AuthResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SecurityConfig securityConfig;

    public AuthService(AuthenticationManager authenticationManager, UserRepository repo, PasswordEncoder encoder, JwtService jwt, SecurityConfig securityConfig) {
        this.authenticationManager = authenticationManager;
        this.userRepository = repo;
        this.passwordEncoder = encoder;
        this.jwtService = jwt;
        this.securityConfig = securityConfig;
    }

    public void register(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Usuario ya existe");
            }
        var user = new Users();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    public String login(String username, String password) {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("No encontrado"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("Credenciales inválidas");
        }

        return jwtService.generateToken(user);
    }
    public AuthResponse authenticate(AuthRequest request) {
     authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String jwt = jwtService.generateToken(user);
        return new AuthResponse(jwt);
    }
}
