package com.coherentsolutions.aqa.java.api.makarevich.model;

import com.coherentsolutions.aqa.java.api.makarevich.constants.UserSex;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@RequiredArgsConstructor
public class User implements Cloneable {
    @NonNull
    private int age;
    @NonNull
    private String name;
    @NonNull
    private UserSex sex;
    private String zipCode;

    @Override
    public User clone() {
        try {
            return (User) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}