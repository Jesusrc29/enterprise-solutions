package com.jesusromero.enterprise.employee.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI employeeManagementOpenApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Employee Management API")
                        .description("Bootstrap API for the enterprise-solutions monorepo")
                        .version("v0.1.0")
                        .contact(new Contact()
                                .name("Jesus Romero")
                                .email("architecture@example.com"))
                        .license(new License()
                                .name("Proprietary Portfolio Project")));
    }
}
