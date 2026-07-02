package com.jesusromero.enterprise.employee.auth.api.controller;

import com.jesusromero.enterprise.employee.auth.api.dto.LoginRequest;
import com.jesusromero.enterprise.employee.auth.api.dto.LoginResponse;
import com.jesusromero.enterprise.employee.auth.application.service.AuthApplicationService;
import com.jesusromero.enterprise.employee.auth.domain.model.AccessToken;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication", description = "Authentication endpoints")
public class AuthController {

    private final AuthApplicationService authApplicationService;

    public AuthController(AuthApplicationService authApplicationService) {
        this.authApplicationService = authApplicationService;
    }

    @PostMapping("/login")
    @Operation(
            summary = "Authenticate user and generate JWT",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Login successful",
                            content = @Content(
                                    schema = @Schema(implementation = LoginResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "accessToken": "jwt-token",
                                                      "tokenType": "Bearer",
                                                      "expiresIn": 3600
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid request payload"),
                    @ApiResponse(responseCode = "401", description = "Invalid credentials")
            }
    )
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        AccessToken token = authApplicationService.login(request);
        return ResponseEntity.ok(new LoginResponse(token.accessToken(), token.tokenType(), token.expiresIn()));
    }
}
