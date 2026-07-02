package com.jesusromero.enterprise.employee.employees.api.controller;

import com.jesusromero.enterprise.employee.employees.api.dto.CreateEmployeeRequest;
import com.jesusromero.enterprise.employee.employees.api.dto.EmployeeResponse;
import com.jesusromero.enterprise.employee.employees.api.dto.UpdateEmployeeRequest;
import com.jesusromero.enterprise.employee.employees.api.mapper.EmployeeApiMapper;
import com.jesusromero.enterprise.employee.employees.application.service.EmployeeApplicationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/employees")
@Tag(name = "Employees", description = "Employee management endpoints")
@SecurityRequirement(name = "bearerAuth")
public class EmployeeController {

    private final EmployeeApplicationService employeeApplicationService;
    private final EmployeeApiMapper employeeApiMapper;

    public EmployeeController(
            EmployeeApplicationService employeeApplicationService,
            EmployeeApiMapper employeeApiMapper
    ) {
        this.employeeApplicationService = employeeApplicationService;
        this.employeeApiMapper = employeeApiMapper;
    }

    @PostMapping
    @Operation(
            summary = "Create a new employee",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Employee created",
                            content = @Content(
                                    schema = @Schema(implementation = EmployeeResponse.class),
                                    examples = @ExampleObject(
                                            value = """
                                                    {
                                                      "id": 1,
                                                      "documentType": "DNI",
                                                      "documentNumber": "12345678",
                                                      "firstName": "John",
                                                      "lastName": "Doe",
                                                      "email": "john.doe@example.com",
                                                      "phone": "999888777",
                                                      "jobTitle": "Backend Engineer",
                                                      "department": "Technology",
                                                      "status": "ACTIVE",
                                                      "hireDate": "2026-07-02",
                                                      "createdAt": "2026-07-02T10:00:00",
                                                      "updatedAt": "2026-07-02T10:00:00"
                                                    }
                                                    """
                                    )
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "Validation error"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "409", description = "Duplicate employee data")
            }
    )
    public ResponseEntity<EmployeeResponse> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
        EmployeeResponse response = employeeApiMapper.toResponse(
                employeeApplicationService.createEmployee(employeeApiMapper.toCreateCommand(request))
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get employee by id")
    public ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeApiMapper.toResponse(employeeApplicationService.getEmployeeById(id)));
    }

    @GetMapping
    @Operation(summary = "List employees with pagination")
    public ResponseEntity<Page<EmployeeResponse>> listEmployees(
            @PageableDefault(page = 0, size = 10) Pageable pageable
    ) {
        Page<EmployeeResponse> response = employeeApplicationService.listEmployees(pageable)
                .map(employeeApiMapper::toResponse);

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update employee")
    public ResponseEntity<EmployeeResponse> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody UpdateEmployeeRequest request
    ) {
        EmployeeResponse response = employeeApiMapper.toResponse(
                employeeApplicationService.updateEmployee(id, employeeApiMapper.toUpdateCommand(request))
        );

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}/deactivate")
    @Operation(summary = "Deactivate employee")
    public ResponseEntity<EmployeeResponse> deactivateEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeApiMapper.toResponse(employeeApplicationService.deactivateEmployee(id)));
    }
}
