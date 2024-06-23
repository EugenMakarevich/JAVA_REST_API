package com.coherentsolutions.aqa.java.api.makarevich;

import com.coherentsolutions.aqa.java.api.makarevich.service.TokenService;

public class GetTokenTest {
    public static void main(String[] args) {
            TokenService tokenService = TokenService.getInstance();
            System.out.println("Write Token: " + tokenService.extractToken(tokenService.getWriteToken()));
            System.out.println("Read Token: " + tokenService.extractToken(tokenService.getReadToken()));
    }
}
