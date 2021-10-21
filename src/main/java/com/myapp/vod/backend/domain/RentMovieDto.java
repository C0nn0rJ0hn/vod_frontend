package com.myapp.vod.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentMovieDto {
    private Integer id;
    private Integer accountId;
    private String rentDate;
    private String expireDate;
    private Integer movieId;

    public RentMovieDto(Integer accountId, Integer movieId) {
        this.accountId = accountId;
        this.movieId = movieId;
    }
}
