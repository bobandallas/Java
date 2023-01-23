package com.example.java26.week3.rest.demo2.controller;

import com.example.java26.week3.rest.demo2.domain.UserInfo;
import com.example.java26.week3.rest.demo2.domain.UserRequestDTO;
import com.example.java26.week3.rest.demo2.serice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<UserRequestDTO> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping(params = "id")
    public ResponseEntity<Map<String, List<UserInfo>>> getUsersIdLargerThan(String id) {
        return new ResponseEntity<>(userService.filterUsersById(id), HttpStatus.OK);
    }

}

