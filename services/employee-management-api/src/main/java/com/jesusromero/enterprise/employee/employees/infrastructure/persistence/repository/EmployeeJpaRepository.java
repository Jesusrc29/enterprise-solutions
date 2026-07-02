package com.jesusromero.enterprise.employee.employees.infrastructure.persistence.repository;

import com.jesusromero.enterprise.employee.employees.infrastructure.persistence.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmployeeJpaRepository extends JpaRepository<EmployeeEntity, Long> {

    Optional<EmployeeEntity> findByDocumentNumber(String documentNumber);

    Optional<EmployeeEntity> findByEmail(String email);
}
