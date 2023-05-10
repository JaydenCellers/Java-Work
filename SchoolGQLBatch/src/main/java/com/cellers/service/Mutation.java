package com.cellers.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.StudentRepository;
import com.cellers.repository.TeacherRepository;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;

@Controller
@SchemaMapping
public class Mutation {

	private SchoolRepository schoolRepository;
	private TeacherRepository teacherRepository;
	private StudentRepository studentRepository;

	@Autowired
	public Mutation(SchoolRepository schoolRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository) {

		this.schoolRepository = schoolRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	@MutationMapping
	public School addSchool(@Argument String name) {
		School school = new School();
		school.setName(name);

		schoolRepository.save(school);

		return school;
	}

	@MutationMapping
	public Teacher addTeacher(@Argument String name, @Argument String subject, @Argument Long schoolId) {

		Teacher teacher = new Teacher();
		School schoolTemp = schoolRepository.getReferenceById(schoolId);
		teacher.setName(name);
		teacher.setSubject(subject);
		teacher.setSchool(schoolTemp);

		teacherRepository.save(teacher);

		return teacher;
	}

	@MutationMapping
	public Student addStudent(@Argument String name, @Argument String gradeLevel, @Argument Long teacherId,
			@Argument Long schoolId) {

		School schoolTemp = schoolRepository.getReferenceById(schoolId);
		Teacher teacherTemp = teacherRepository.getReferenceById(teacherId);

		Student student = new Student();
		student.setName(name);
		student.setGradeLevel(gradeLevel);
		student.setTeacher(teacherTemp);
		student.setSchool(schoolTemp);

		studentRepository.save(student);

		return student;
	}

	@MutationMapping
	public Teacher updateTeacher(@Argument Long id, @Argument String name, @Argument String subject,
			@Argument Long schoolId) throws NotFoundException {

		Optional<Teacher> optTeacher = teacherRepository.findById(id);

		if (optTeacher.isPresent()) {
			Teacher teacher = optTeacher.get();

			if (name != null)
				teacher.setName(name);
			if (subject != null)
				teacher.setSubject(subject);
			if (schoolId != null) {
				School schoolTemp = schoolRepository.getReferenceById(schoolId);
				teacher.setSchool(schoolTemp);
			}

			teacherRepository.save(teacher);
			return teacher;
		}

		throw new NotFoundException();
	}

	@MutationMapping
	public Student updateStudent(@Argument Long id, @Argument String name, @Argument String gradeLevel,
			@Argument Long schoolId, @Argument Long teacherId) throws NotFoundException {

		Optional<Student> optStudent = studentRepository.findById(id);

		if (optStudent.isPresent()) {
			Student student = optStudent.get();

			if (name != null)
				student.setName(name);
			if (gradeLevel != null)
				student.setGradeLevel(gradeLevel);
			if (schoolId != null) {
				School schoolTemp = schoolRepository.getReferenceById(schoolId);
				student.setSchool(schoolTemp);
			}
			if (teacherId != null) {
				Teacher teacherTemp = teacherRepository.getReferenceById(teacherId);
				student.setTeacher(teacherTemp);
			}

			studentRepository.save(student);
			return student;
		}

		throw new NotFoundException();
	}

	@MutationMapping
	public boolean deleteSchool(@Argument Long id) {
		schoolRepository.deleteById(id);
		return true;
	}

	@MutationMapping
	public boolean deleteTeacher(@Argument Long id) {
		teacherRepository.deleteById(id);
		return true;
	}

	@MutationMapping
	public boolean deleteStudent(@Argument Long id) {
		studentRepository.deleteById(id);
		return true;
	}
}