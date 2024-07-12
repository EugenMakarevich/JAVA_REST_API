package com.coherentsolutions.aqa.java.api.makarevich.factory;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.github.javafaker.Faker;

import java.util.ArrayList;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static User generateRandomUser() {
        int age = faker.number().numberBetween(10, 100);
        String name = faker.name().fullName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex);
    }

    public static User generateRandomUser(String zipCode) {
        int age = faker.number().numberBetween(10, 100);
        String name = faker.name().fullName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex, zipCode);
    }

    public static User generateRandomUser(String name, UserSex sex) {
        int age = faker.number().numberBetween(10, 100);
        return new User(age, name, sex);
    }

    public static boolean isDuplicatedZipcodesPresent(ArrayList<String> list, String zipcode) {
        int count = 0;
        for (String z : list) {
            if (z.equals(zipcode)) {
                count++;
                if (count > 1) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String generateRandomZipCode() {
        return faker.address().zipCode();
    }

    private static UserSex getRandomUserSex() {
        return faker.options().option(UserSex.class);
    }
}