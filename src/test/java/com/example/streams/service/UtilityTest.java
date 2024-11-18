package com.example.streams.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.*;
import java.nio.file.Files;

class UtilityTest {

    @Test
    void testReadFromFile() {
        try {
            String s = UtilityTest.readFromFileToString("jsonsamples/SampleResponse.json");
            System.out.println(s);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testCreateObjectFromJsonFile() {
        try {
            SomeClass someClass = getSomeClass("jsonsamples/data.json");
            System.out.println(someClass.firstName + " " +someClass.getLastName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static String readFromFileToString(String filePath) throws IOException {
        File resource = new ClassPathResource(filePath).getFile();
        byte[] byteArray = Files.readAllBytes(resource.toPath());
        return new String(byteArray);
    }

    public static SomeClass getSomeClass(String filePath) throws IOException {
        // create Object Mapper
        ObjectMapper mapper = new ObjectMapper();
        // read JSON file and map/convert to java POJO
        SomeClass someClassObject = null;
//            someClassObject = mapper.readValue(new File(filePath), SomeClass.class);
        someClassObject = mapper.readValue(new ClassPathResource(filePath).getFile(), SomeClass.class);

        return someClassObject;
    }
}