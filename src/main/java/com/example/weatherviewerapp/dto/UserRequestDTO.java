package com.example.weatherviewerapp.dto;



import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {
    private String login;
    private String password;

}
