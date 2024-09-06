package com.example.weatherviewerapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Sessions")
public class Session {
@Id
@Column(name = "id", length = 36, nullable = false)
    private String id;
@OneToOne
@JoinColumn(name = "user_id", nullable = false)
    private User user;
@Column(name = "expiresAt", nullable = false)
    private Timestamp timestamp;

}
