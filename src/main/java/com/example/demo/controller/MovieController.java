package com.example.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
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
	
	private HashMap<String, Movie> movies;
	
	//@Autowired
	private MovieService movieService;
	
	@Autowired
	public MovieController(MovieService movieService) {
//		movies = new HashMap<String, Movie>() {{
//			put(UUID.randomUUID().toString(), new Movie("Movie 1", "Description 1", "Director 1"));
//			put(UUID.randomUUID().toString(), new Movie("Movie 2", "Description 2", "Director 2"));
//			put(UUID.randomUUID().toString(), new Movie("Movie 3", "Description 3", "Director 3"));
//			put(UUID.randomUUID().toString(), new Movie("Movie 4", "Description 4", "Director 4"));
//		}};
		this.movieService = movieService;
		this.movieService.save(new Movie("Movie_1", "Description 1", "Director 1"));
		this.movieService.save(new Movie("Movie_2", "Description 2", "Director 2"));
		this.movieService.save(new Movie("Movie_3", "Description 3", "Director 3"));
		this.movieService.save(new Movie("Movie_4", "Description 4", "Director 4"));
		
//		movieService.save(new Movie("Movie 1", "Description 1", "Director 1"));
//		movieService.save(new Movie("Movie 2", "Description 2", "Director 2"));
//		movieService.save(new Movie("Movie 3", "Description 3", "Director 3"));
//		movieService.save(new Movie("Movie 4", "Description 4", "Director 4"));
	}
	
	@GetMapping("")
	public List<MovieDto> getMovies(){
//		return movies.entrySet().stream().map(current -> {
//		      var id = current.getKey();
//		      var movie = current.getValue();
//
//		      return new MovieDto(id, movie.getTitle(), movie.getDescription(), movie.getDirector());
//		    }).collect(Collectors.toList());
		var movies = movieService.getAll();
		return movies.stream().map(current -> {
			return new MovieDto(current.getId(), current.getTitle(), current.getDescription(), current.getDirector());
		}).collect(Collectors.toList());
	}
	
	@GetMapping("{title}")
	public MovieDto getMovie(@PathVariable("title") String movieTitle) {
		//Movie movie = movies.get(movieId);
		Movie movie = movieService.get(movieTitle);
		return new MovieDto(movie.getId(), movie.getTitle(), movie.getDescription(), movie.getDirector());
	}
	
	@DeleteMapping("{title}")
	public void deleteMovie(@PathVariable("title") String movieTitle) {
		//movies.remove(movieId);
		movieService.delete(movieTitle);
	}
	
	@PostMapping("")
	public Movie saveMovie(@RequestBody MovieDto dto) {
		//var id = UUID.randomUUID().toString();
	
		//movies.put(id, new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
		
	    //return new MovieDto(id, dto.getTitle(), dto.getDescription(), dto.getDirector());
		return movieService.save(new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
	}

	@PutMapping("{title}")
	public Movie updateMovie(@PathVariable("title") String movieTitle, @RequestBody MovieDto dto) {
		//movies.put(movieId, new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
		
		return movieService.update(movieTitle, new Movie(dto.getTitle(), dto.getDescription(), dto.getDirector()));
	}

}
