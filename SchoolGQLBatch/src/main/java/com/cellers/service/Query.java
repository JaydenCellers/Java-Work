package com.cellers.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.StudentRepository;
import com.cellers.repository.TeacherRepository;

@Controller
public class Query {
	
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

	@QueryMapping
	public Iterable<School> findAllSchools() {
		return schoolRepository.findAll();
	}
	
	@QueryMapping
	public School findSchoolById(Long id) {
		return schoolRepository.findById(id).get();
	}

	@QueryMapping
	public Iterable<Teacher> findAllTeachers() {
		return teacherRepository.findAll();
	}
	
	@QueryMapping
	public Teacher findTeacherById(Long id) {
		return teacherRepository.findById(id).get();
	}

	@QueryMapping
	public Iterable<Student> findAllStudents() {
		return studentRepository.findAll();
	}
	
	@QueryMapping
	public Student findStudentById(Long id) {
		return studentRepository.findById(id).get();
	}
	
	@BatchMapping(typeName = "Teacher", field = "school")
	public Map<Teacher, School> schoolT(List<Teacher> teachers){
		//System.out.println("Getting teacher info for " + teachers.size() + " teacher(s)");
		return teachers
				.stream()
				.collect(Collectors.toMap(teacher -> teacher,
						teacher -> teacher.getSchool()));
	}
	
	@BatchMapping(typeName = "Student", field = "school")
	public Map<Student, School> schoolS(List<Student> students){
		//System.out.println("Getting student info for " + students.size() + " student(s)");
		return students
				.stream()
				.collect(Collectors.toMap(student -> student,
						student -> student.getSchool()));
	}
	
	/*@SchemaMapping(typeName = "Teacher")
	School school(Teacher teacher) {
		System.out.println("Getting School for teacher # " + teacher.getId());
		return teacher.getSchool();
	}*/
	
	
}
