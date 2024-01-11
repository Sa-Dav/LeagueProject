package com.example.demo.repository;

import com.example.demo.domain.Subscriber;
import com.example.demo.dto.SubscriberDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<Subscriber, Long> {

    @Query("SELECT new com.example.demo.dto.SubscriberDTO(s.lastMatchId, s.email, r.puuid) FROM Subscriber s join s.riotAccount r")
    List<SubscriberDTO> findSubscriberNewGame();

    @Query("SELECT  s FROM Subscriber s where s.email = :email")
    Subscriber findByEmail(String email);
}
