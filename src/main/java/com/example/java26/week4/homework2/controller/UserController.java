package com.example.java26.week4.homework2.controller;

import com.example.java26.week4.homework2.pojo.dto.UserRequestDTO;
import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO;
import com.example.java26.week4.homework2.pojo.entity.User;
import com.example.java26.week4.homework2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }


    @GetMapping
    public ResponseEntity<UserResponseDTO> getAll(){
        return new ResponseEntity<>(userService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addOne(@RequestBody UserRequestDTO userRequestDTO){
        try {
            String result = userService.insertOne(userRequestDTO.getProvider());
            if(result == "success"){
                return ResponseEntity.status(HttpStatus.CREATED).build();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }


}
