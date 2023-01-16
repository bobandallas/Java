package com.example.java26.week3.rest.homework.repository.impl;

import com.example.java26.week3.rest.homework.pojo.dto.StudentResponseDTO;
import com.example.java26.week3.rest.homework.pojo.entity.Student;
import com.example.java26.week3.rest.homework.repository.StudentRepository;
import com.example.java26.week3.rest.homework.repository.StudentRepositoryBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class StudentRepositoryImpl implements StudentRepository{
    private final StudentRepositoryBase base;

    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    public StudentRepositoryImpl(StudentRepositoryBase base){
        this.base = base;
    }


    @Override
    public String addOne(Student student) {
        base.saveAndFlush(student);
        return "success";
    }

    @Override
    @Transactional
    public String updateOne(int id, Student student) {
        Student temp = base.getById(id);
        temp.setName(student.getName());
        temp.setAge(student.getAge());
        temp.setVegetarianism(student.isVegetarianism());

        base.saveAndFlush(temp);
        return "success";
    }

    @Override
    public List<Student> findAll() {
        return base.findAll();
    }

    @Override
    public Student getById(int id) {
        return base.getById(id);
    }

    @Override
    public String deleteOne(int id){
        base.deleteById(id);
        return "success";
    }


}
