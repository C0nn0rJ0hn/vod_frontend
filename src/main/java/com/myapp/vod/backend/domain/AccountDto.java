package com.myapp.vod.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

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
    private Role role;
    private boolean loggedIn;
    private Integer userId;
    private Integer watchlistId;
    private Integer buyId;
    private List<Integer> rentMoviesId = new ArrayList<>();
    private List<Integer> rentTvShowsId = new ArrayList<>();

    public AccountDto(String password, String email, String country, String language, Role role, Integer userId) {
        this.password = password;
        this.email = email;
        this.country = country;
        this.language = language;
        this.role = role;
        this.userId = userId;
    }

    public AccountDto(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
