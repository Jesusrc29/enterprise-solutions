package com.jesusromero.enterprise.employee.employees.api.mapper;

import com.jesusromero.enterprise.employee.config.MapStructConfig;
import com.jesusromero.enterprise.employee.employees.api.dto.CreateEmployeeRequest;
import com.jesusromero.enterprise.employee.employees.api.dto.EmployeeResponse;
import com.jesusromero.enterprise.employee.employees.api.dto.UpdateEmployeeRequest;
import com.jesusromero.enterprise.employee.employees.application.command.CreateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.application.command.UpdateEmployeeCommand;
import com.jesusromero.enterprise.employee.employees.domain.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapStructConfig.class)
public interface EmployeeApiMapper {

    CreateEmployeeCommand toCreateCommand(CreateEmployeeRequest request);

    UpdateEmployeeCommand toUpdateCommand(UpdateEmployeeRequest request);

    @Mapping(target = "status", expression = "java(employee.status().name())")
    EmployeeResponse toResponse(Employee employee);
}
