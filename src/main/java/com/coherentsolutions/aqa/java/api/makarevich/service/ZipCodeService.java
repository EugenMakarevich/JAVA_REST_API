package com.coherentsolutions.aqa.java.api.makarevich.service;

import java.util.ArrayList;

public class ZipCodeService {
    public boolean isDuplicatedZipcodesPresent(ArrayList<String> list, String zipcode) {
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
}
