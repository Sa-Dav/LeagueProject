package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriberCommand {
    @NotBlank(message = "Must not be blank")
    private String email;

    @NotBlank(message = "Must not be blank")
    private String gameName;

    @NotBlank(message = "Must not be blank")
    private String tagLine;
}
