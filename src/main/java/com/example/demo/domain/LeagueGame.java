package com.example.demo.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

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

    private LocalDateTime savedDate;

    private String bans;

    @OneToMany(mappedBy = "leagueGame", fetch = FetchType.LAZY)
    private List<Teams> teams;


    public LeagueGame(String matchId, String filePath, LocalDateTime savedDate, String bans) {
        this.matchId = matchId;
        this.filePath = filePath;
        this.savedDate = savedDate;
        this.bans = bans;
    }
}

