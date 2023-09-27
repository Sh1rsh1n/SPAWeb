package ru.sh.spaweb.entity;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Data
@ToString
public class Employee {

    private static int counter = 1000;
    @Getter
    private Integer id;

    private String name;

    private String surname;

    private String department;

    public Employee(String name, String surname, String department) {
        this.name = name;
        this.surname = surname;
        this.department = department;
        id = ++counter;
    }

    public Employee() {
        id = ++counter;
    }
}
