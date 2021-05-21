package com.example.demo.domain;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
public class Movie {
	
	@Getter
	@Setter
//	@Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Id @GeneratedValue(generator="system-uuid")
	@GenericGenerator(name="system-uuid", strategy = "uuid")
	private String id;
	
	@Getter
	@Setter
	private String title;
	
	@Getter
	@Setter
	private String description;
	
	@Getter
	@Setter
	private String director;
	
	public Movie() {
		
	}
	
	public Movie(String title, String description, String director) {
		this.title = title;
		this.description = description;
		this.director = director;
	}
	
	public Movie(String id, String title, String description, String director) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.director = director;
	}

}
