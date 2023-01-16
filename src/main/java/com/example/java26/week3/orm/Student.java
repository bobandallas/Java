package com.example.java26.week3.orm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "student")
@Entity //告诉hibernate这个class可以进行监管，对应的student table进到student class里面
@Data //lombok帮我们创建get set
@NoArgsConstructor  //创建一个constructor没有任何参数
@AllArgsConstructor //创建一个constructor有所有的参数
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 如何生成这个value 目前是让数据库帮忙生成id
                                                        // 建议给id设置上自增的sequence
                                                        // 为什么不让用户生成id？
                                                        // 答案：避免重复
    private String id;

    @Column(name = "name")
    private String name;

    //一个student 对应多个 teacher
    @OneToMany(mappedBy = "stu", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = false)
    // mappedBy指向的是join table(teacher_student)的reference name,
    // CascadeType 会决定是否 删除本行数据会不会连带着删除join table的数据
    // orphanRemoval 可以对list进行操作然后commit进行删除or crud操作    orphanRemoval
    private List<Teacher_Student> teacher_students;

    public List<Teacher_Student> getTeacher_students() {
        return teacher_students;
    }

    public void setTeacher_students(List<Teacher_Student> ts) {
        this.teacher_students = ts;
    }

    public void addTeacher_student(Teacher_Student ts) {
        this.teacher_students.add(ts);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
