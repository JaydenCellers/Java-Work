package com.cellers.resolver;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.StudentRepository;
import com.cellers.repository.TeacherRepository;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;

@Component
public class Mutation implements GraphQLMutationResolver {

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

	public School addSchool(String name) {
		School school = new School();
		school.setName(name);

		schoolRepository.save(school);

		return school;
	}

	public Teacher addTeacher(String name, String subject, Long schoolId) {
		Teacher teacher = new Teacher();
		teacher.setName(name);
		teacher.setSchool(new School(schoolId));
		teacher.setSubject(subject);

		teacherRepository.save(teacher);

		return teacher;
	}

	public Student addStudent(String name, String gradeLevel, Long teacherId, Long schoolId) {
		Student student = new Student();
		student.setName(name);
		student.setGradeLevel(gradeLevel);
		student.setTeacher(new Teacher(teacherId));
		student.setSchool(new School(schoolId));

		studentRepository.save(student);

		return student;
	}

	public boolean deleteSchool(Long id) {
		schoolRepository.deleteById(id);
		return true;
	}

	public boolean deleteTeacher(Long id) {
		teacherRepository.deleteById(id);
		return true;
	}

	public boolean deleteStudent(Long id) {
		studentRepository.deleteById(id);
		return true;
	}

	public Teacher updateTeacher(Long id, String name, String subject, Long schoolId) throws NotFoundException {
		Optional<Teacher> optTeacher = teacherRepository.findById(id);

		if (optTeacher.isPresent()) {
			Teacher teacher = optTeacher.get();

			if (name != null)
				teacher.setName(name);
			if (subject != null)
				teacher.setSubject(subject);
			if (schoolId != null)
				teacher.setSchool(new School(schoolId));

			teacherRepository.save(teacher);
			return teacher;
		}

		throw new NotFoundException();
	}
	
	public Student updateStudent(Long id, String name, String gradeLevel, Long schoolId, Long teacherId) throws NotFoundException {
		Optional<Student> optStudent = studentRepository.findById(id);

		if (optStudent.isPresent()) {
			Student student = optStudent.get();

			if (name != null)
				student.setName(name);
			if (gradeLevel != null)
				student.setGradeLevel(gradeLevel);
			if (schoolId != null)
				student.setSchool(new School(schoolId));
			if (teacherId != null)
				student.setTeacher(new Teacher(teacherId));
			studentRepository.save(student);
			return student;
		}

		throw new NotFoundException();
	}
}