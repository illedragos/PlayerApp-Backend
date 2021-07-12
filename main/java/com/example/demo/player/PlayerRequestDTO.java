package com.example.demo.player;

import java.time.LocalDate;

public class PlayerRequestDTO {
    private String name;
    private LocalDate dateOfBirth;
    private String email;
    private String rank;

    public PlayerRequestDTO(String name, LocalDate dateOfBirth, String email, String rank) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.rank = rank;
    }

    public PlayerRequestDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }
}
