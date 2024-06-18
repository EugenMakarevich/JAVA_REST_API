package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;

import java.io.IOException;

public class GetTokenTest {
    public static void main(String[] args) {
        try {
            TokenService tokenService = TokenService.getInstance();
            System.out.println("Write Token: " + tokenService.extractToken(tokenService.getWriteToken()));
            System.out.println("Read Token: " + tokenService.extractToken(tokenService.getReadToken()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
