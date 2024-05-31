package org.example;

import lombok.Data;

@Data
public class EmployeeDTO {

    private int id;
    private String name;
    private String email;
    private int departmentId;
    private String departmentName;
}
