package com.example.streams;

import com.example.streams.entities.Employee;
import com.example.streams.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalDouble;

@SpringBootApplication
public class Application {
    @Autowired
    public EmployeeService employeeService;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        List<Employee> employees = new ArrayList<>();
        employees.add(new Employee(1, "Frank", 20));
        employees.add(new Employee(2, "Lisa", 50));
        employees.add(new Employee(3, "Colm", 49));
        employees.add(new Employee(4, "Fred", 33));
        employees.add(new Employee(5, "Andy", 31));
        employees.add(new Employee(6, "Cillian", 28));
        employees.add(new Employee(7, "Dave", 25));
        employees.add(new Employee(8, "Hugo", 37));
        employees.add(new Employee(9, "Mark", 47));
        employees.add(new Employee(10, "Mary", 47));

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        Double average = employeeService.concurrentAverage(employees);
        stopWatch.stop();
        System.out.println("that took: " + stopWatch.getTotalTimeNanos());
        System.out.println("Average is: " + average);
        System.out.println("............");

        stopWatch.start();
        double linearAverage = employeeService.linearAverage(employees);
        stopWatch.stop();
        System.out.println("that took: " + stopWatch.getTotalTimeNanos());
        System.out.println("Average is: " + linearAverage);
        System.out.println("............");

        stopWatch.start();
        double calculateAverageOldWay = employeeService.calculateAverage(employees);
        stopWatch.stop();
        System.out.println("that took: " + stopWatch.getTotalTimeNanos());
        System.out.println("Old fashioned Average is: " + calculateAverageOldWay);
        System.out.println("............");

        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };

    }
}



