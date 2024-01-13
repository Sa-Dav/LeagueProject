package com.example.demo.exceptionHandling;

public class ThisAccountAlreadySubscribedException extends RuntimeException {

    private String gameName;

    public ThisAccountAlreadySubscribedException(String gameName) {
        this.gameName = gameName;
    }

    public String getGameName() {
        return gameName;
    }
}
