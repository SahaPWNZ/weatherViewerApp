package com.example.weatherviewerapp.dto;



import lombok.*;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String login;
    private String password;

}
