package com.example.weatherviewerapp.dto;



import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserRequestDTO {
    private String login;
    private String password;

}
