package com.jesusromero.enterprise.employee.employees.infrastructure.persistence;

import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import com.jesusromero.enterprise.employee.employees.domain.repository.EmployeeRepository;
import com.jesusromero.enterprise.employee.employees.infrastructure.persistence.mapper.EmployeePersistenceMapper;
import com.jesusromero.enterprise.employee.employees.infrastructure.persistence.repository.EmployeeJpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class EmployeePersistenceRepository implements EmployeeRepository {

    private final EmployeeJpaRepository employeeJpaRepository;
    private final EmployeePersistenceMapper employeePersistenceMapper;

    public EmployeePersistenceRepository(
            EmployeeJpaRepository employeeJpaRepository,
            EmployeePersistenceMapper employeePersistenceMapper
    ) {
        this.employeeJpaRepository = employeeJpaRepository;
        this.employeePersistenceMapper = employeePersistenceMapper;
    }

    @Override
    public Employee save(Employee employee) {
        return employeePersistenceMapper.toDomain(
                employeeJpaRepository.save(employeePersistenceMapper.toEntity(employee))
        );
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeJpaRepository.findById(id)
                .map(employeePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Employee> findByDocumentNumber(String documentNumber) {
        return employeeJpaRepository.findByDocumentNumber(documentNumber)
                .map(employeePersistenceMapper::toDomain);
    }

    @Override
    public Optional<Employee> findByEmail(String email) {
        return employeeJpaRepository.findByEmail(email)
                .map(employeePersistenceMapper::toDomain);
    }

    @Override
    public Page<Employee> findAll(Pageable pageable) {
        return employeeJpaRepository.findAll(pageable)
                .map(employeePersistenceMapper::toDomain);
    }
}
