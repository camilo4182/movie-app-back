package com.example.demo.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.domain.Movie;

@Repository
public interface MovieRepository extends CrudRepository<Movie, String> {
	
	List<Movie> findAll();
	Movie findByTitle(String title);

}
