package com.example.demo.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.MovieDto;
import com.example.demo.domain.Movie;
import com.example.demo.service.interfaces.MovieService;

@RestController()
@RequestMapping("movies")
@CrossOrigin
public class MovieController {
	
	private MovieService movieService;
	
	@Autowired
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
		this.movieService.save(new Movie("Movie_1", "Description 1", "Director 1"));
		this.movieService.save(new Movie("Movie_2", "Description 2", "Director 2"));
		this.movieService.save(new Movie("Movie_3", "Description 3", "Director 3"));
		this.movieService.save(new Movie("Movie_4", "Description 4", "Director 4"));
	}
	
	@GetMapping("")
	public List<MovieDto> getMovies(){
		var movies = movieService.getAll();
		return movies.stream().map(current -> {
			return new MovieDto(current.getId(), current.getTitle(), current.getDescription(), current.getDirector());
		}).collect(Collectors.toList());
	}
	
	@GetMapping("{title}")
	public MovieDto getMovie(@PathVariable("title") String title) {
		Movie movie = movieService.get(title);
		return new MovieDto(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getDirector());
	}
	
	@DeleteMapping("{title}")
	public void deleteMovie(@PathVariable("title") String title) {
		movieService.delete(title);
	}
	
	@PostMapping("")
	public Movie saveMovie(@RequestBody MovieDto dto) {
		return movieService.save(new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
	}

	@PutMapping("{title}")
	public Movie updateMovie(@PathVariable("title") String title, @RequestBody MovieDto dto) {
		return movieService.update(dto.getId(), new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
	}

}
