package com.example.weatherviewerapp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "sessions")
public class UserSession {
    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @Column(name = "activetime", nullable = false)
    private Timestamp activeTime;

    @Override
    public String toString() {
        return "UserSession{" +
                "id='" + id + '\'' +
                ", user=" + user +
                ", timestamp=" + activeTime +
                '}';
    }
}
