package com.example.java26.week4.homework2.repository;

import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO;
import com.example.java26.week4.homework2.pojo.dto.UserResponseDTO.*;
import com.example.java26.week4.homework2.pojo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
