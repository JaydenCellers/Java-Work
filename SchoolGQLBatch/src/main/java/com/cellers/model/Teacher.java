package com.cellers.model;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@JsonInclude(Include.NON_ABSENT)
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "subject")
	private String subject;
	
	@ManyToOne
	@JoinColumn(name = "school_id", nullable = false, updatable = true)
	private School school;
	
	public Teacher(Long id) {
		this.id = id;
	}
}
