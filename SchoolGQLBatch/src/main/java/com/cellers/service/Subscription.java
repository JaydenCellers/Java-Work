package com.cellers.service;

import java.time.Duration;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.SubscriptionMapping;
import org.springframework.stereotype.Controller;

import com.cellers.model.School;
import com.cellers.model.Student;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.cellers.repository.StudentRepository;
import com.cellers.repository.TeacherRepository;

import reactor.core.publisher.Flux;

@Controller
public class Subscription {

	private SchoolRepository schoolRepository;
	private TeacherRepository teacherRepository;
	private StudentRepository studentRepository;

	@Autowired
	public Subscription(SchoolRepository schoolRepository, TeacherRepository teacherRepository,
			StudentRepository studentRepository) {

		this.schoolRepository = schoolRepository;
		this.teacherRepository = teacherRepository;
		this.studentRepository = studentRepository;
	}

	@SubscriptionMapping
	public Flux<List<School>> schools() {
		return Flux.fromStream(Stream.generate(() -> schoolRepository.findAll())).delayElements(Duration.ofSeconds(3));
	}
	
	@SubscriptionMapping
	public Flux<List<Teacher>> teachers() {
		return Flux.fromStream(Stream.generate(() -> teacherRepository.findAll())).delayElements(Duration.ofSeconds(3));
	}
	
	@SubscriptionMapping
	public Flux<List<Student>> students() {
		return Flux.fromStream(Stream.generate(() -> studentRepository.findAll())).delayElements(Duration.ofSeconds(3));
	}
}
