package com.example.demo.player;


import javax.persistence.Transient;
import java.time.LocalDate;

public class PlayerResponseDTO {
    private String name;
    private Integer age;//age will not be a column, it will be calculated
    private LocalDate dateOfBirth;
    private String email;
    private RankEnum rank;
    private String externalId;

    public PlayerResponseDTO() {
    }

    public PlayerResponseDTO(String name, Integer age, LocalDate dateOfBirth, String email, RankEnum rank,String externalId) {
        this.name = name;
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.rank = rank;
        this.externalId=externalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
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
}
