package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class Teams {
    private String teamID;
    private Boolean win;

    @Override
    public String toString() {
        return "Teams{" +
                "teamID='" + teamID + '\'' +
                ", win=" + win +
                '}';
    }

    public String getTeamID() {
        return teamID;
    }

    public Teams setTeamID(String teamID) {
        this.teamID = teamID;
        return this;
    }

    public Boolean getWin() {
        return win;
    }

    public Teams setWin(Boolean win) {
        this.win = win;
        return this;
    }
}
