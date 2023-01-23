package com.example.java26.week4.homework2.service;

import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO;
import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO.*;
import com.example.java26.week4.homework2.pojo.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    UserResponseDTO getAll();
    String insertOne(UserDTO user);

}
