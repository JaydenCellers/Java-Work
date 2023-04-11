package com.cellers.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.StudentRepository;
import com.cellers.repository.TeacherRepository;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;

@Component
public class Query implements GraphQLQueryResolver {

	private SchoolRepository schoolRepository;
	private TeacherRepository teacherRepository;
	private StudentRepository studentRepository;

	@Autowired
	public Query(SchoolRepository schoolRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository) {

		this.schoolRepository = schoolRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	public Iterable<School> findAllSchools() {
		return schoolRepository.findAll();
	}
	
	public School findSchoolById(Long id) {
		return schoolRepository.findById(id).get();
	}

	public Iterable<Teacher> findAllTeachers() {
		return teacherRepository.findAll();
	}
	
	public Teacher findTeacherById(Long id) {
		return teacherRepository.findById(id).get();
	}

	public Iterable<Student> findAllStudents() {
		return studentRepository.findAll();
	}
	
	public Student findStudentById(Long id) {
		return studentRepository.findById(id).get();
	}

}