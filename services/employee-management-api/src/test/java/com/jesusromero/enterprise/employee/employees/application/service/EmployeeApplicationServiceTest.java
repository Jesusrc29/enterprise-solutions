package com.jesusromero.enterprise.employee.employees.application.service;

import com.jesusromero.enterprise.employee.employees.application.command.CreateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.application.command.UpdateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.domain.exception.DuplicateEmployeeException;
import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import com.jesusromero.enterprise.employee.employees.domain.model.EmployeeStatus;
import com.jesusromero.enterprise.employee.employees.domain.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmployeeApplicationServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeApplicationService employeeApplicationService;

    @Test
    void shouldCreateEmployeeSuccessfully() {
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                "DNI",
                "12345678",
                "John",
                "Doe",
                "john.doe@example.com",
                "999888777",
                "Backend Engineer",
                "Technology",
                LocalDate.of(2026, 7, 2)
        );

        Employee savedEmployee = employee(1L, "12345678", "john.doe@example.com", EmployeeStatus.ACTIVE);

        when(employeeRepository.findByDocumentNumber(command.documentNumber())).thenReturn(Optional.empty());
        when(employeeRepository.findByEmail(command.email())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(savedEmployee);

        Employee result = employeeApplicationService.createEmployee(command);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.status()).isEqualTo(EmployeeStatus.ACTIVE);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void shouldRejectDuplicateDocumentNumber() {
        CreateEmployeeCommand command = new CreateEmployeeCommand(
                "DNI",
                "12345678",
                "John",
                "Doe",
                "john.doe@example.com",
                null,
                null,
                null,
                LocalDate.of(2026, 7, 2)
        );

        when(employeeRepository.findByDocumentNumber("12345678"))
                .thenReturn(Optional.of(employee(10L, "12345678", "existing@example.com", EmployeeStatus.ACTIVE)));

        assertThatThrownBy(() -> employeeApplicationService.createEmployee(command))
                .isInstanceOf(DuplicateEmployeeException.class)
                .hasMessageContaining("document number");
    }

    @Test
    void shouldGetEmployeeById() {
        Employee employee = employee(1L, "12345678", "john.doe@example.com", EmployeeStatus.ACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        Employee result = employeeApplicationService.getEmployeeById(1L);

        assertThat(result.id()).isEqualTo(1L);
        assertThat(result.documentNumber()).isEqualTo("12345678");
    }

    @Test
    void shouldDeactivateEmployee() {
        Employee activeEmployee = employee(1L, "12345678", "john.doe@example.com", EmployeeStatus.ACTIVE);
        Employee inactiveEmployee = employee(1L, "12345678", "john.doe@example.com", EmployeeStatus.INACTIVE);

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(activeEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(inactiveEmployee);

        Employee result = employeeApplicationService.deactivateEmployee(1L);

        assertThat(result.status()).isEqualTo(EmployeeStatus.INACTIVE);
        verify(employeeRepository).save(any(Employee.class));
    }

    @Test
    void shouldUpdateEmployeeSuccessfully() {
        Employee currentEmployee = employee(1L, "12345678", "john.doe@example.com", EmployeeStatus.ACTIVE);
        UpdateEmployeeCommand command = new UpdateEmployeeCommand(
                "DNI",
                "87654321",
                "Jane",
                "Doe",
                "jane.doe@example.com",
                "999111222",
                "Tech Lead",
                "Technology",
                LocalDate.of(2026, 7, 2)
        );
        Employee updatedEmployee = new Employee(
                1L,
                command.documentType(),
                command.documentNumber(),
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone(),
                command.jobTitle(),
                command.department(),
                EmployeeStatus.ACTIVE,
                command.hireDate(),
                currentEmployee.createdAt(),
                currentEmployee.updatedAt()
        );

        when(employeeRepository.findById(1L)).thenReturn(Optional.of(currentEmployee));
        when(employeeRepository.findByDocumentNumber(command.documentNumber())).thenReturn(Optional.empty());
        when(employeeRepository.findByEmail(command.email())).thenReturn(Optional.empty());
        when(employeeRepository.save(any(Employee.class))).thenReturn(updatedEmployee);

        Employee result = employeeApplicationService.updateEmployee(1L, command);

        assertThat(result.firstName()).isEqualTo("Jane");
        assertThat(result.documentNumber()).isEqualTo("87654321");
    }

    private Employee employee(Long id, String documentNumber, String email, EmployeeStatus status) {
        return new Employee(
                id,
                "DNI",
                documentNumber,
                "John",
                "Doe",
                email,
                "999888777",
                "Backend Engineer",
                "Technology",
                status,
                LocalDate.of(2026, 7, 2),
                LocalDateTime.of(2026, 7, 2, 10, 0),
                LocalDateTime.of(2026, 7, 2, 10, 0)
        );
    }
}
