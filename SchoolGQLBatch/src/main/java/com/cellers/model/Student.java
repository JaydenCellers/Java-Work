package com.cellers.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "grade_level")
	private String gradeLevel;
	
	@ManyToOne
	@JoinColumn(name = "school_id", nullable = true, updatable = true)
	private School school;
	
	@ManyToOne
	@JoinColumn(name = "teacher_id", nullable = true, updatable = true)
	private Teacher teacher;
}