package com.jesusromero.enterprise.employee.employees.infrastructure.persistence.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import com.jesusromero.enterprise.employee.employees.infrastructure.persistence.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface EmployeePersistenceMapper {

    EmployeeEntity toEntity(Employee employee);

    Employee toDomain(EmployeeEntity employeeEntity);
}
