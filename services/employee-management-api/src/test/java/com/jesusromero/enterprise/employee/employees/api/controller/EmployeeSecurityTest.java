package com.jesusromero.enterprise.employee.employees.api.controller;

import com.jesusromero.enterprise.employee.employees.api.mapper.EmployeeApiMapper;
import com.jesusromero.enterprise.employee.employees.application.service.EmployeeApplicationService;
import com.jesusromero.enterprise.employee.security.CustomUserDetailsService;
import com.jesusromero.enterprise.employee.security.JwtAuthenticationFilter;
import com.jesusromero.enterprise.employee.security.JwtService;
import com.jesusromero.enterprise.employee.security.RestAuthenticationEntryPoint;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@AutoConfigureMockMvc
@Import(RestAuthenticationEntryPoint.class)
class EmployeeSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeApplicationService employeeApplicationService;

    @MockitoBean
    private EmployeeApiMapper employeeApiMapper;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @Test
    void shouldReturnUnauthorizedWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isUnauthorized());
    }
}
