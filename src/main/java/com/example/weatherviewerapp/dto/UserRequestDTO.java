package com.example.weatherviewerapp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class UserRequestDTO {
    private String login;
    private String password;
}
