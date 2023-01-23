package com.example.java26.week3.rest.demo2.serice;

import com.example.java26.week3.rest.demo2.domain.UserInfo;
import com.example.java26.week3.rest.demo2.domain.UserRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{

    private final RestTemplate restTemplate;

    @Value("${user.rest.url}")
    private String url;

    @Autowired
    public UserServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserRequestDTO getAllUsers() {
        UserRequestDTO userRequestDTO = restTemplate.getForObject(url, UserRequestDTO.class);
        return userRequestDTO;
    }

    @Override
    public Map<String, List<UserInfo>> filterUsersById(String id) {
        UserRequestDTO userRequestDTO = restTemplate.getForObject(url, UserRequestDTO.class);
        List<UserInfo> userInfos = userRequestDTO
                .getUserInfos()
                .stream()
                .filter(u -> Long.valueOf(u.getId()) >= Long.valueOf(id))
                .collect(Collectors.toList());
        Map<String, List<UserInfo>> res = new HashMap<>();
        res.put("content", userInfos);
        return res;
    }

}
