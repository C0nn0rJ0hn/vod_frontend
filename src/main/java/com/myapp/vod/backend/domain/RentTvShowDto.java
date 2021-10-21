package com.myapp.vod.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RentTvShowDto {
    private Integer id;
    private Integer accountId;
    private String rentDate;
    private String expireDate;
    private Integer tvShowId;

    public RentTvShowDto(Integer accountId, Integer tvShowId) {
        this.accountId = accountId;
        this.tvShowId = tvShowId;
    }
}
