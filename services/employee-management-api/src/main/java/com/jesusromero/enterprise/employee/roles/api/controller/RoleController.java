package com.jesusromero.enterprise.employee.roles.api.controller;

import com.jesusromero.enterprise.employee.roles.api.dto.RoleResponse;
import com.jesusromero.enterprise.employee.roles.api.mapper.RoleApiMapper;
import com.jesusromero.enterprise.employee.roles.application.service.RoleApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
@Tag(name = "Roles", description = "Role catalog endpoints")
@SecurityRequirement(name = "bearerAuth")
public class RoleController {

    private final RoleApplicationService roleApplicationService;
    private final RoleApiMapper roleApiMapper;

    public RoleController(
            RoleApplicationService roleApplicationService,
            RoleApiMapper roleApiMapper
    ) {
        this.roleApplicationService = roleApplicationService;
        this.roleApiMapper = roleApiMapper;
    }

    @GetMapping
    @Operation(summary = "List roles")
    public ResponseEntity<List<RoleResponse>> listRoles() {
        return ResponseEntity.ok(
                roleApplicationService.listRoles().stream()
                        .map(roleApiMapper::toResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get role by id")
    public ResponseEntity<RoleResponse> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleApiMapper.toResponse(roleApplicationService.getRoleById(id)));
    }
}
