package com.example.demo.player;
//this interface will interact with our database
//this is responsible with data acces
//will extend JpaRepository
// next we need to connect things using depencies injection
//this interface will be used inside of our Service

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {

    //here we need to specify what class is for; we have to pass the type of primary key

    //SELECT * FROM student WHERE email = ?
    @Query("SELECT p FROM Player p WHERE p.email = ?1")
    Optional<Player> findPlayerByEmail(String email);

    Optional<Player> findPlayerByExternalId(String externalId);

    //Optional<Player> findPlayerById(Long id);

}
