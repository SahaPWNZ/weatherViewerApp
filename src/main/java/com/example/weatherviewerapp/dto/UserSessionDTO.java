package com.example.weatherviewerapp.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserSessionDTO {
    private String id;
    private Long userId;
    private Timestamp timestamp;
}

