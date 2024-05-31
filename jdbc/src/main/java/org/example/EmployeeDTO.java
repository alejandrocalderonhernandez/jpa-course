package org.example;

import lombok.Data;

@Data
public class EmployeeDTO {

    private Integer id;
    private String name;
    private String email;
    private Integer departmentId;
    private String departmentName;

}
