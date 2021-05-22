package com.example.demo.service.implementation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;
import com.example.demo.service.interfaces.MovieService;

@Component
public class MovieServiceImp implements MovieService {
	
	private MovieRepository movieRepository;
	
	@Autowired
	public MovieServiceImp(MovieRepository movieRepository) {
		this.movieRepository = movieRepository;
	}

	@Override
	public List<Movie> getAll() {
		return movieRepository.findAll();
	}

	@Override
	public Movie get(String title) {
		return movieRepository.findByTitle(title);
	}

	@Override
	public Movie save(Movie movie) {
		return movieRepository.save(movie);
	}

	@Override
	public Movie update(String id, Movie movie) {
		var oldMovie = movieRepository.findById(id).get();
		movie.setId(oldMovie.getId());
		return movieRepository.save(movie);
	}

	@Override
	public void delete(String title) {
		Movie movie = movieRepository.findByTitle(title);
		movieRepository.delete(movie);
	}
}
