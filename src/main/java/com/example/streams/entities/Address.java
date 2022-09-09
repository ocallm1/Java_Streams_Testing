package com.example.streams.entities;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Address {
    private String estate;
    private String town;
    private String country;
}
