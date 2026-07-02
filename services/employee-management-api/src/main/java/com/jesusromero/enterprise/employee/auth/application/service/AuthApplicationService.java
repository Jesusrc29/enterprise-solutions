package com.jesusromero.enterprise.employee.auth.application.service;

import com.jesusromero.enterprise.employee.auth.api.dto.LoginRequest;
import com.jesusromero.enterprise.employee.auth.domain.model.AccessToken;
import com.jesusromero.enterprise.employee.security.JwtService;
import com.jesusromero.enterprise.employee.shared.exception.UnauthorizedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthApplicationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthApplicationService(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public AccessToken login(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.username(), request.password())
            );

            Set<String> roles = authentication.getAuthorities()
                    .stream()
                    .map(GrantedAuthority::getAuthority)
                    .collect(Collectors.toSet());

            String token = jwtService.generateToken(authentication.getName(), roles);
            return new AccessToken(token, "Bearer", jwtService.getExpirationSeconds());
        } catch (BadCredentialsException exception) {
            throw new UnauthorizedException("Invalid username or password");
        }
    }
}
