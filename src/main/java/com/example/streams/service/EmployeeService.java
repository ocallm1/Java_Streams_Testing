package com.example.streams.service;

import com.example.streams.entities.Employee;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.OptionalDouble;


@Service
public class EmployeeService {

    /**
     * Java method which uses map reduce to calculate average of list of
     * employees in JDK 8.
     *
     * @param employees List of employees
     * @return average age of given list of Employees
     */
    public Double concurrentAverage(List<? extends Employee> employees) {
        OptionalDouble asDouble = employees.parallelStream().mapToInt(Employee::getAge).average();

        return asDouble.isPresent() ? asDouble.getAsDouble() : 0D;
    }

    /**
     * Java method which uses map reduce to calculate average of list of employees in JDK 8+
     * using streams
     *
     * @param employees List of Employees
     * @return average age of given list of Employees
     */
    public double linearAverage(List<? extends Employee> employees) {
        return employees.stream().mapToInt(Employee::getAge)
                .average()
                .getAsDouble();
    }

    /**
     * Java Method to calculate average from a list of object without using
     * lambdas, doing it on classical java way.
     *
     * @param employees List of Employees
     * @return average age of given list of com.example.streams.entities.Employee
     */
    public double calculateAverage(List<? extends Employee> employees) {
        int totalEmployee = employees.size();
        double sum = 0;
        for (Employee e : employees) {
            sum += e.getAge();
        }

        return (sum / totalEmployee);
    }

    public Integer sumOfAgesInt(List<? extends Employee> employees) {
        int sum = employees.stream().mapToInt(Employee::getAge).sum();

        return sum;
    }

    public Double sumOfAgesListDouble(List<? extends Employee> employees) {
        Double reduce = employees.stream().mapToInt(Employee::getAge).asDoubleStream().reduce(
                0, Double::sum);

        return reduce;
    }
}

