package com.example.java26.week3.rest.demo2.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserRequestDTO {
    @JsonProperty("data")
    private List<UserInfo> userInfos;
}
