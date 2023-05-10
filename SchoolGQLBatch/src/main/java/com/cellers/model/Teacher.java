package com.cellers.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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
