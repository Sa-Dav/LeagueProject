package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LeagueGame {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String matchId;

    private String filePath;

    private Date savedDate;

    public LeagueGame(String matchId, String filePath) {
        this.matchId = matchId;
        this.filePath = filePath;
    }

    public LeagueGame setMatchId(String matchId) {
        this.matchId = matchId;
        return this;
    }

    public LeagueGame setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }
}

