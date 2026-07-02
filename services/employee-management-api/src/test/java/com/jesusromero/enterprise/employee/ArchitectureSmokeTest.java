package com.jesusromero.enterprise.employee;

import com.jesusromero.enterprise.employee.users.infrastructure.persistence.repository.UserJpaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(properties = {
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration,"
                + "org.springframework.boot.autoconfigure.flyway.FlywayAutoConfiguration"
})
class ArchitectureSmokeTest {

    @Autowired
    private EmployeeManagementApiApplication application;

    @MockitoBean
    private UserJpaRepository userJpaRepository;

    @Test
    void contextLoads() {
        assertThat(application).isNotNull();
    }
}
