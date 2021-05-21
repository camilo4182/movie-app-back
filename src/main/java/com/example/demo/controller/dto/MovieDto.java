package com.example.demo.controller.dto;

import lombok.Getter;
import lombok.Setter;

public class MovieDto {
	
	@Getter
    @Setter
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
	
	public MovieDto() {
		
	}
	
	public MovieDto(String id, String title, String description, String director) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.director = director;
	}

}
