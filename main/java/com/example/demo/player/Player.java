package com.example.demo.player;

import lombok.Builder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;


@Entity
@Table
public class Player {
    @Id
    @SequenceGenerator(
            name = "player_sequence",
            sequenceName = "player_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "player_sequence"
    )

    private Long id;

    private String externalId = UUID.randomUUID().toString();
    private String name;
    @Transient
    private Integer age;//age will not be a column, it will be calculated

    private LocalDate dateOfBirth;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private RankEnum rank;

    public Player() {

    }


    public Player(String name, LocalDate dateOfBirth, String email, RankEnum rank) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.rank = rank;
    }

    public Player(Long id, String name, LocalDate dateOfBirth, String email, RankEnum rank) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.rank = rank;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return Period.between(dateOfBirth, LocalDate.now()).getYears();
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public RankEnum getRank() {
        return rank;
    }

    public void setRank(RankEnum rank) {
        this.rank = rank;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    @Override
    public String toString() {
        return "Player{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", dateOfBirth=" + dateOfBirth +
                ", email='" + email + '\'' +
                ", rank=" + rank +
                ", externalId='" + externalId + '\'' +
                '}';
    }
}
