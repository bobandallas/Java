package com.example.java26.week4.homework2.pojo.dto;

import com.example.java26.week4.homework2.pojo.entity.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class UserResponseDTO {

    @JsonProperty("data")
    private List<UserDTO> data;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class UserDTO {
        private String firstName;

        private String lastName;

        private String middleName;

        private String dob;

        public UserDTO(User user){
            this.firstName = user.getFirstName();
            this.lastName = user.getLastName();
            this.middleName = user.getMiddleName();
            this.dob = user.getDob();
        }

    }
}
