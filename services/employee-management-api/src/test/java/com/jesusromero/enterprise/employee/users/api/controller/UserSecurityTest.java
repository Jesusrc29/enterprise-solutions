package com.jesusromero.enterprise.employee.users.api.controller;

import com.jesusromero.enterprise.employee.security.CustomUserDetailsService;
import com.jesusromero.enterprise.employee.security.JwtAuthenticationFilter;
import com.jesusromero.enterprise.employee.security.JwtService;
import com.jesusromero.enterprise.employee.security.RestAuthenticationEntryPoint;
import com.jesusromero.enterprise.employee.security.SecurityConfig;
import com.jesusromero.enterprise.employee.users.api.mapper.UserApiMapper;
import com.jesusromero.enterprise.employee.users.application.service.UserApplicationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        value = UserController.class,
        excludeAutoConfiguration = UserDetailsServiceAutoConfiguration.class
)
@AutoConfigureMockMvc
@Import({SecurityConfig.class, RestAuthenticationEntryPoint.class, JwtAuthenticationFilter.class})
class UserSecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserApplicationService userApplicationService;

    @MockitoBean
    private UserApiMapper userApiMapper;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private JwtService jwtService;

    @Test
    void shouldReturnUnauthorizedWhenTokenIsMissing() throws Exception {
        mockMvc.perform(get("/api/v1/users/1"))
                .andExpect(status().isUnauthorized());
    }
}
