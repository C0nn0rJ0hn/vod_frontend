package com.myapp.vod.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDto {
    private Integer id;
    private String createdAt;
    private String updatedAt;
    private String password;
    private String email;
    private String country;
    private String language;
    private Integer userId;

    public AccountDto(String password, String email, String country, String language, Integer userId) {
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.userId = userId;
    }

    public AccountDto(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
