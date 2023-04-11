package com.cellers.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cellers.model.School;

public interface SchoolRepository extends JpaRepository<School, Long>{

}
