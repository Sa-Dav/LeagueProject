package com.example.demo.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RiotAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String puuid;
    private String gameName;
    private String tagLine;
    private String lastMatchId;
    private LocalDateTime lastChange;
    @ManyToOne
    private Subscriber subscriber;


    @Override
    public String toString() {
        return "RiotAccount{" +
                "id=" + id +
                ", puuid='" + puuid + '\'' +
                ", gameName='" + gameName + '\'' +
                ", tagLine='" + tagLine + '\'' +
                ", lastMatchId='" + lastMatchId + '\'' +
                ", lastChange=" + lastChange +
                ", subscriber=" + subscriber +
                '}';
    }
}
