package com.coherentsolutions.aqa.java.api.makarevich.factory;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.github.javafaker.Faker;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex.FEMALE;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex.MALE;

public class UserFactory {

    private static final Faker faker = new Faker();

    public static User generateRandomUser() {
        int age = generateRandomAge();
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex);
    }

    public static User generateRandomUser(String zipCode) {
        int age = generateRandomAge();
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex, zipCode);
    }

    public static User generateRandomUser(int age) {
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex);
    }

    public static User generateRandomUser(UserSex sex) {
        int age = generateRandomAge();
        String name = generateRandomName();
        return new User(age, name, sex);
    }

    public static User generateRandomUser(String name, UserSex sex) {
        int age = generateRandomAge();
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

    public static String generateRandomName() {
        return faker.name().fullName();
    }

    public static Integer generateRandomAge() {
        return faker.number().numberBetween(10, 90);
    }

    public static UserSex getRandomUserSex() {
        return faker.options().option(UserSex.class);
    }

    public static UserSex getOppositeUserSex(UserSex sex) {
        if (sex == MALE) {
            return FEMALE;
        } else {
            return MALE;
        }
    }
}