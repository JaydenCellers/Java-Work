package com.cellers.request;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateTeacherRequest {

	private String firstName;

	private String lastName;

	private List<CreateStudentRequest> studentList;

}
