package com.cellers.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonInclude(Include.NON_NULL)
public class Output {
	
	Long id;
	String name;
	String subject;
	School school;
	String gradeLevel;
	Teacher teacher;
	Boolean deleteTeacher;
	Boolean deleteSchool;
	Boolean deleteStudent;

}
