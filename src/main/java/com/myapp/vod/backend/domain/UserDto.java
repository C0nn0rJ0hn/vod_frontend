package com.myapp.vod.backend.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
    private Integer id;
    private String name;
    private String lastname;
    private String birthDate;
    private Gender gender;

    public UserDto(String name, String lastname, String birthDate, Gender gender) {
        this.name = name;
        this.lastname = lastname;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
