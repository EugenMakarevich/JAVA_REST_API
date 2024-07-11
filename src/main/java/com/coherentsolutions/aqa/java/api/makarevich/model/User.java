package com.coherentsolutions.aqa.java.api.makarevich.model;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private int age;
    private String name;
    private UserSex sex;
    private String zipCode;
}