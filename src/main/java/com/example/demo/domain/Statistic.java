package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String individualPosition;
    private String championName;
    private int championId;
    private String puuid;
    private int timePlayed;
    private int totalDamageDealtToChampions;
    private Boolean win;


    @ManyToOne
    private LeagueGame leagueGame;

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", individualPosition='" + individualPosition + '\'' +
                ", championName='" + championName + '\'' +
                ", championId=" + championId +
                ", puuid='" + puuid + '\'' +
                ", timePlayed=" + timePlayed +
                ", totalDamageDealtToChampions=" + totalDamageDealtToChampions +
                ", win=" + win +
                ", leagueGame=" + leagueGame +
                '}';
    }
}
