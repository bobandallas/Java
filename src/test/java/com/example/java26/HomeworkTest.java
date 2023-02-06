package com.example.java26;

import com.example.java26.week4.homework2.controller.UserController;
import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO;
import com.example.java26.week4.homework2.service.UserService;
import com.example.java26.week4.homework2.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HomeworkTest {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void testAddUser() throws Exception {

        UserResponseDTO.UserDTO testDTO = new UserResponseDTO.UserDTO("1", "2", "3", "4");

        // condition one with provider
        when(userService.insertOne(testDTO)).thenReturn("success");
        Map<String, UserResponseDTO.UserDTO> rightChoice = new HashMap<>();
        rightChoice.put("provider", testDTO);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(rightChoice)))
                .andReturn();

        assertEquals(201, mvcResult.getResponse().getStatus());


        //condition three without provider, it will failed without provider
        when(userService.insertOne(testDTO)).thenReturn("success");
        MvcResult mvcResult3 = mockMvc.perform(MockMvcRequestBuilders.post("/user").contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testDTO)))
                .andReturn();

        assertEquals(201, mvcResult3.getResponse().getStatus());



    }

    @Test
    public void testGetAllUsers() throws Exception {

        //build test result
        UserResponseDTO.UserDTO testDTO = new UserResponseDTO.UserDTO("1", "2", "3", "4");
        List<UserResponseDTO.UserDTO> testList= new ArrayList<>();
        testList.add(testDTO);
        UserResponseDTO testResult = new UserResponseDTO();
        testResult.setData(testList);

        // always return test result
        when(userService.getAll()).thenReturn(testResult);

        // mvc request and expect get result
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/user").accept(MediaType.APPLICATION_JSON)).andReturn();
        assertEquals(200, mvcResult.getResponse().getStatus());
        UserResponseDTO result = new ObjectMapper().readValue(mvcResult.getResponse().getContentAsString(), UserResponseDTO.class);
        assertEquals(testList.get(0), result.getData().get(0));
    }

}
