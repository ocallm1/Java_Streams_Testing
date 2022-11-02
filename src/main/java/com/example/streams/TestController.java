package com.example.streams;

import com.example.streams.entities.Address;
import com.example.streams.entities.Employee;
import com.example.streams.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TestController {
    @Autowired
    public EmployeeService employeeService;

    @GetMapping("/health")
    public String health() {
        Double v = employeeService.calculateAverage(createEmployees());
        return "Hello & Welcome to CloudKatha !!! "+ v.toString();
    }

    public  List<Employee> createEmployees() {
        List<Employee> employees = new ArrayList<>();
        Employee emp1 = Employee.builder().address(Address.builder().town("Cork").build()).age(10).id(1).name("Johnny").build();
        employees.add(emp1);
        Employee emp2 = Employee.builder().address(Address.builder().town("Cork").build()).age(20).id(2).name("Frank").build();
        employees.add(emp2);
        Employee emp3 = Employee.builder().address(Address.builder().build()).age(50).id(3).name("Lisa").build();
        employees.add(emp3);
        Address address = Address.builder().town("Dublin").country("Ireland").build();
        Employee emp4 = Employee.builder().address(address).age(50).id(3).name("Lisa").build();
        employees.add(emp4);

        return employees;
    }
}