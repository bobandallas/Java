package com.example.java26.week4.homework2.service.impl;

import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO;
import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO.*;
import com.example.java26.week4.homework2.pojo.entity.User;
import com.example.java26.week4.homework2.repository.UserRepository;
import com.example.java26.week4.homework2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Override
    public UserResponseDTO getAll(){
        List<User> temp = (List<User>) userRepository.findAll();
        UserResponseDTO res = new UserResponseDTO();
        res.setData(temp.stream()
                        .map((e) -> new UserDTO(e))
                        .collect(Collectors.toList()));

        return res;
    }
    @Override
    public String insertOne(UserDTO user){
        User temp = new User();
        temp.setDob(user.getDob());
        temp.setFirstName(user.getFirstName());
        temp.setLastName(user.getLastName());
        temp.setMiddleName(user.getMiddleName());
        System.out.println(temp);
        userRepository.saveAndFlush(temp);
        return "success";
    };
}
