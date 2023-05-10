package com.cellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cellers.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Long> {

}
