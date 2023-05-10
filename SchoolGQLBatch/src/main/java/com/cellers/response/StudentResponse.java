package com.cellers.response;

import com.cellers.model.School;
import com.cellers.model.Teacher;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class StudentResponse extends QueryResponse {
	
	private Long id;
	
	private String name;
	
	private String gradeLevel;
	
	private School school;
	
	private Teacher teacher;

}
