package com.example.weatherviewerapp.dto;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class UserSessionDTO {
    private String GUID;
    private Long userId;
    private Timestamp timestamp;
}

