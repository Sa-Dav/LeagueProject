package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Teams {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String teamID;
    private Boolean win;
    private String role;
    private String champId;
    @ManyToOne
    private LeagueGame leagueGame;

    @Override
    public String toString() {
        return "Teams{" +
                "teamID='" + teamID + '\'' +
                ", win=" + win +
                '}';
    }
}
