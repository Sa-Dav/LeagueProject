package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SummonerDTO {
    @JsonProperty("puuid")
    private String puuid;

    @JsonProperty("gameName")
    private String summonerNAme;

    @JsonProperty("tagLine")
    private String tagLine;

}
