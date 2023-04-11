package com.cellers.resolver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.cellers.model.School;
import com.cellers.model.Teacher;
import com.cellers.repository.SchoolRepository;
import com.coxautodev.graphql.tools.GraphQLResolver;

@Component
public class TeacherResolver implements GraphQLResolver<Teacher> {
	@Autowired
	private SchoolRepository schoolRepository;

	public TeacherResolver(SchoolRepository schoolRepository) {
		this.schoolRepository = schoolRepository;
	}

	public School getSchool(Teacher teacher) {
		return schoolRepository.findById(teacher.getSchool().getId()).orElseThrow(null);
	}
}