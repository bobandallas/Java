package com.example.java26.week3.rest.demo2.serice;

import com.example.java26.week3.rest.demo2.domain.UserInfo;
import com.example.java26.week3.rest.demo2.domain.UserRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    UserRequestDTO getAllUsers();
    Map<String, List<UserInfo>> filterUsersById(String id);
}
