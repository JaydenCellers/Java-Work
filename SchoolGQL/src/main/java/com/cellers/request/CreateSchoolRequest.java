package com.cellers.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateSchoolRequest {

	private String name;

	private List<CreateTeacherRequest> teacherList;
	
	private List<CreateStudentRequest> studentList;

}
