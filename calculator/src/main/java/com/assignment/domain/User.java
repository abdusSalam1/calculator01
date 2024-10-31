package com.assignment.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@AllArgsConstructor
@Builder
@Getter
public class User {

    private final String name;
    private final UserType type;
    private final LocalDate affiliationDate;

    public boolean isEmployee() {
        return type.equals(UserType.EMPLOYEE);
    }

    public boolean isAffiliate() {
        return type.equals(UserType.AFFILIATE);
    }

    public boolean isOldCustomer() {
        return ChronoUnit.YEARS.between(affiliationDate, LocalDate.now()) > 2;
    }
}
