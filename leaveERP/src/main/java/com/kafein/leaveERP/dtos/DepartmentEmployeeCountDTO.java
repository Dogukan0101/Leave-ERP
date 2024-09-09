package com.kafein.leaveERP.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class DepartmentEmployeeCountDTO {

    private String departmentName;

    private Long employeeCount;
}
