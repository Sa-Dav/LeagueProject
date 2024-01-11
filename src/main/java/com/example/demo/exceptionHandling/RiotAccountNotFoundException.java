package com.example.demo.exceptionHandling;

public class RiotAccountNotFoundException extends RuntimeException {

    private String gameName;
    private String tagLine;

    public RiotAccountNotFoundException(String gameName, String tagLine) {
        this.gameName = gameName;
        this.tagLine = tagLine;
    }

    public String getGameName() {
        return gameName;
    }

    public String getTagLine() {
        return tagLine;
    }
}
