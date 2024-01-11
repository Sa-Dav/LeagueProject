package com.example.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Subscriber {
    @Id
    private Long id;

    private String lastMatchId;

    private String email;

    @OneToMany(mappedBy = "subscriber", fetch = FetchType.LAZY)
    private List<RiotAccount> riotAccount;

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", lastMatchId='" + lastMatchId + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
