package com.coherentsolutions.aqa.java.api.makarevich.factory;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import com.coherentsolutions.aqa.java.api.makarevich.model.User;
import com.github.javafaker.Faker;
import io.qameta.allure.Step;

import java.util.ArrayList;

import static com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex.FEMALE;
import static com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex.MALE;

public class UserFactory {
    private static final Faker faker = new Faker();

    @Step("Generate random user without zip code")
    public static User generateRandomUser() {
        int age = generateRandomAge();
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex);
    }

    @Step("Generate random user with zip code")
    public static User generateRandomUser(String zipCode) {
        int age = generateRandomAge();
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex, zipCode);
    }

    @Step("Generate random user with age specified")
    public static User generateRandomUser(int age) {
        String name = generateRandomName();
        UserSex sex = getRandomUserSex();
        return new User(age, name, sex);
    }

    @Step("Generate random user with sex specified")
    public static User generateRandomUser(UserSex sex) {
        int age = generateRandomAge();
        String name = generateRandomName();
        return new User(age, name, sex);
    }

    @Step("Generate random user with name and sex specified")
    public static User generateRandomUser(String name, UserSex sex) {
        int age = generateRandomAge();
        return new User(age, name, sex);
    }

    @Step("Check if duplicated code present in database")
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

    @Step("Generate random zip code")
    public static String generateRandomZipCode() {
        return faker.address().zipCode();
    }

    @Step("Generate random name")
    public static String generateRandomName() {
        return faker.name().fullName();
    }

    @Step("Generate random age from 10 to 90")
    public static Integer generateRandomAge() {
        return faker.number().numberBetween(10, 90);
    }

    @Step("Generate random sex")
    public static UserSex getRandomUserSex() {
        return faker.options().option(UserSex.class);
    }

    @Step("Get opposite sex")
    public static UserSex getOppositeUserSex(UserSex sex) {
        if (sex == MALE) {
            return FEMALE;
        } else {
            return MALE;
        }
    }
}