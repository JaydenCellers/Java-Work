package com.cellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cellers.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

}
