package com.example.demo.component.api.movie;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.everit.json.schema.loader.SchemaLoader;
import org.json.JSONTokener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.domain.Movie;
import com.example.demo.repository.MovieRepository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

import lombok.SneakyThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { "spring.config.additional-location=classpath:component-test.yml"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class ListMoviesTest {
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private MovieRepository movieRepository;
	
	@BeforeEach
	public void setUp() {
		var movie = new Movie("Scarface", "The world is yours", "Brian De Palma");
		movieRepository.save(movie);
	}
	
	@Test
	@SneakyThrows
	public void listMoviesSuccessfully() {
        var response = mockMvc.perform(get("/movies")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
    }
	
	@Test
	@SneakyThrows
	public void listMoviesWithRightSchema() {
        var response = mockMvc.perform(get("/movies")).andReturn().getResponse();

        assertThat(response.getStatus(), equalTo(HttpStatus.OK.value()));
        assertThat(response.getContentType(), equalTo(MediaType.APPLICATION_JSON.toString()));
        
        var jsonSchema = new JSONObject(new JSONTokener(ListMoviesTest.class.getResourceAsStream("/movies.json")));
        var jsonArray = new JSONArray(response.getContentAsString());
        
        var schema = SchemaLoader.load(jsonSchema);
        schema.validate(jsonArray);
    }

}
