package com.assignment.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
public class UserModel {

    private String name;
    private String type;
    private LocalDate affiliationDate;
}
