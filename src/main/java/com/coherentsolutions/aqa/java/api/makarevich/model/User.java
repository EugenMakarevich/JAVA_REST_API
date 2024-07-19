package com.coherentsolutions.aqa.java.api.makarevich.model;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Cloneable {
    private Integer age;
    private String name;
    private UserSex sex;
    private String zipCode;

    public User(Integer age, String name, UserSex sex) {
        this.age = age;
        this.name = name;
        this.sex = sex;
    }

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}