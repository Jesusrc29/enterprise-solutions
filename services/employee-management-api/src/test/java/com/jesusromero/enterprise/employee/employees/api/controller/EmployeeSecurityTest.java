package com.jesusromero.enterprise.employee.employees.api.controller;

import com.jesusromero.enterprise.employee.employees.api.mapper.EmployeeApiMapper;
import com.jesusromero.enterprise.employee.employees.application.service.EmployeeApplicationService;
import com.jesusromero.enterprise.employee.security.CustomUserDetailsService;
import com.jesusromero.enterprise.employee.security.JwtAuthenticationFilter;
import com.jesusromero.enterprise.employee.security.JwtService;
import com.jesusromero.enterprise.employee.security.RestAuthenticationEntryPoint;
import com.jesusromero.enterprise.employee.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = EmployeeController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class
)
@AutoConfigureMockMvc
@Import({SecurityConfig.class, RestAuthenticationEntryPoint.class, JwtAuthenticationFilter.class})
class EmployeeSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private EmployeeApplicationService employeeApplicationService;

    @MockitoBean
    private EmployeeApiMapper employeeApiMapper;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldReturnUnauthorizedWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/employees/1"))
                .andExpect(status().isUnauthorized());
    }
}
