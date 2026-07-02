package com.jesusromero.enterprise.employee.employees.application.service;

import com.jesusromero.enterprise.employee.employees.application.command.CreateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.application.command.UpdateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.domain.exception.DuplicateEmployeeException;
import com.jesusromero.enterprise.employee.employees.domain.exception.EmployeeNotFoundException;
import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import com.jesusromero.enterprise.employee.employees.domain.model.EmployeeStatus;
import com.jesusromero.enterprise.employee.employees.domain.repository.EmployeeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeApplicationService {

    private final EmployeeRepository employeeRepository;

    public EmployeeApplicationService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee createEmployee(CreateEmployeeCommand command) {
        ensureDocumentNumberAvailable(command.documentNumber(), null);
        ensureEmailAvailable(command.email(), null);

        Employee employee = new Employee(
                null,
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
                null,
                null
        );

        return employeeRepository.save(employee);
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    public Page<Employee> listEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    public Employee updateEmployee(Long id, UpdateEmployeeCommand command) {
        Employee currentEmployee = getEmployeeById(id);

        ensureDocumentNumberAvailable(command.documentNumber(), id);
        ensureEmailAvailable(command.email(), id);

        Employee updatedEmployee = new Employee(
                currentEmployee.id(),
                command.documentType(),
                command.documentNumber(),
                command.firstName(),
                command.lastName(),
                command.email(),
                command.phone(),
                command.jobTitle(),
                command.department(),
                currentEmployee.status(),
                command.hireDate(),
                currentEmployee.createdAt(),
                currentEmployee.updatedAt()
        );

        return employeeRepository.save(updatedEmployee);
    }

    public Employee deactivateEmployee(Long id) {
        Employee currentEmployee = getEmployeeById(id);

        Employee deactivatedEmployee = new Employee(
                currentEmployee.id(),
                currentEmployee.documentType(),
                currentEmployee.documentNumber(),
                currentEmployee.firstName(),
                currentEmployee.lastName(),
                currentEmployee.email(),
                currentEmployee.phone(),
                currentEmployee.jobTitle(),
                currentEmployee.department(),
                EmployeeStatus.INACTIVE,
                currentEmployee.hireDate(),
                currentEmployee.createdAt(),
                currentEmployee.updatedAt()
        );

        return employeeRepository.save(deactivatedEmployee);
    }

    private void ensureDocumentNumberAvailable(String documentNumber, Long currentEmployeeId) {
        employeeRepository.findByDocumentNumber(documentNumber)
                .filter(employee -> !employee.id().equals(currentEmployeeId))
                .ifPresent(employee -> {
                    throw new DuplicateEmployeeException("document number", documentNumber);
                });
    }

    private void ensureEmailAvailable(String email, Long currentEmployeeId) {
        employeeRepository.findByEmail(email)
                .filter(employee -> !employee.id().equals(currentEmployeeId))
                .ifPresent(employee -> {
                    throw new DuplicateEmployeeException("email", email);
                });
    }
}
