package com.example.streams;

import com.example.streams.entities.Address;
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

@SpringBootApplication
public class Application {
    @Autowired
    public EmployeeService employeeService;

    /**
     * @param args Optional.ofNullable(otaAirCheckInRQ)
     *             .map(OTAAirCheckInRQ::getPassengerFlightInfos)
     *             .filter(l -> !l.isEmpty())
     *             .map(l -> l.get(0))
     *             .map(PassengerFlightInfo::getBookingInfo)
     *             .map(BookingInfo::getBookingReferenceIDs)
     *             .filter(l -> !l.isEmpty())
     *             .map(l -> l.get(0))
     *             .map(BookingReferenceID::getID)
     *             .orElse(null);
     */
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
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

        int sumOfAgesInt = employeeService.sumOfAgesInt(employees);
        System.out.println("sumOfAgesInt is: " + sumOfAgesInt);
        System.out.println("............");

        double sumOfAgesDouble = employeeService.sumOfAgesListDouble(employees);
        System.out.println("sumOfAgesDouble is: " + sumOfAgesDouble);
        System.out.println("............");

        double sumOfAgesFromCorkDouble = employeeService.sumOfAgesListDoubleFromCork(employees);
        System.out.println("sumOfAgesFromCorkDouble is: " + sumOfAgesFromCorkDouble);
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



