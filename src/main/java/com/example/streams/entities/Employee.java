package com.example.streams.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private final int id;
    private final String name;
    private final int age;
    private final Address address;

}
