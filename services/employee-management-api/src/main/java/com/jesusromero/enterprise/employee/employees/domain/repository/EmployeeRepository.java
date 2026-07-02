package com.jesusromero.enterprise.employee.employees.domain.repository;

import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface EmployeeRepository {

    Employee save(Employee employee);

    Optional<Employee> findById(Long id);

    Optional<Employee> findByDocumentNumber(String documentNumber);

    Optional<Employee> findByEmail(String email);

    Page<Employee> findAll(Pageable pageable);
}
