package com.cellers.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.TeacherRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class StudentResolver implements GraphQLResolver<Student> {
	@Autowired
	private SchoolRepository schoolRepository;
	private TeacherRepository teacherRepository;

	public StudentResolver(SchoolRepository schoolRepository, TeacherRepository teacherRepository) {
		this.schoolRepository = schoolRepository;
		this.teacherRepository = teacherRepository;
	}

	public School getSchool(Student student) {
		return schoolRepository.findById(student.getSchool().getId()).orElseThrow(null);
	}

	public Teacher getTeacher(Student student) {
		return teacherRepository.findById(student.getTeacher().getId()).orElseThrow(null);
	}
}