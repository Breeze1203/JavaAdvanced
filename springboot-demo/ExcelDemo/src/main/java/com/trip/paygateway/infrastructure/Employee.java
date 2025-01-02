package com.trip.paygateway.infrastructure;

public class Employee {
    @ExcelColumn(name = "Name")
    private String name;

    @ExcelColumn(name = "Designation")
    private String designation;

    @ExcelColumn(name = "Department")
    private String department;

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", department='" + department + '\'' +
                '}';
    }
}
