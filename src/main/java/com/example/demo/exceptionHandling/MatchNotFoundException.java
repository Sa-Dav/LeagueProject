package com.example.demo.exceptionHandling;

public class MatchNotFoundException extends RuntimeException {
    private String matchId;

    public MatchNotFoundException(String matchId) {
        this.matchId = matchId;
    }

    public String getMatchId() {
        return matchId;
    }
}
