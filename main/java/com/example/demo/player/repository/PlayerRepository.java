package com.example.demo.player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    @Query("SELECT p FROM Player p WHERE p.email = ?1")
    Optional<Player> findPlayerByEmail(String email);

    Optional<Player> findPlayerByExternalId(String externalId);

}
