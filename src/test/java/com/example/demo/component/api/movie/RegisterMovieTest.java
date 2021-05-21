package com.example.demo.component.api.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.repository.MovieRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class RegisterMovieTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Test
	@SneakyThrows
	public void registerMovieSuccessfully() {
		var movie = new RegisterMovieTest.RegisterMovieRequestBody();
		movie.setTitle("Toy Story");
		movie.setDescription("It's play time");
		movie.setDirector("Pixar");
		
		var registerMovieRequestBody = new ObjectMapper().writeValueAsString(movie);
		
		var response = mockMvc.perform(
                post("/movies")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(registerMovieRequestBody))
                .andReturn()
                .getResponse();
		
		var movieResponse = new ObjectMapper().readValue(response.getContentAsString(), RegisterMovieResponse.class);
		
		// Asserts Http Response
        assertThat(movieResponse.getTitle(), equalTo("Toy Story"));
        assertThat(movieResponse.getDescription(), equalTo("It's play time"));
        assertThat(movieResponse.getDirector(), equalTo("Pixar"));
        assertThat(movieResponse.getId(), notNullValue());
        
        // Database Asserts
        var dbQuery = movieRepository.findById(movieResponse.getId());
        assertThat(dbQuery.isPresent(), is(true));
        
        var movieDB = dbQuery.get();
        assertThat(movieResponse.getTitle(), equalTo("Toy Story"));
        assertThat(movieResponse.getDescription(), equalTo("It's play time"));
        assertThat(movieResponse.getDirector(), equalTo("Pixar"));
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	static class RegisterMovieRequestBody {
		private String title;
		private String description;
		private String director;
	}
	
	@Getter
	@Setter
	@NoArgsConstructor
	static class RegisterMovieResponse {
		private String id;
		private String title;
		private String description;
		private String director;
	}

}
