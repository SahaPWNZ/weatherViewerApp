package com.example.weatherviewerapp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Data
@AllArgsConstructor
public class User {
    private final Long id;
    private final String login;
    private final String password;
}
