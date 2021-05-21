package com.example.demo.service.interfaces;

import java.util.List;

import com.example.demo.domain.Movie;

public interface MovieService {
	
	List<Movie> getAll();
	Movie get(String title);
	Movie save(Movie movie);
	Movie update(String title, Movie movie);
	void delete(String title);

}
