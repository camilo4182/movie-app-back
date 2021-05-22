package com.example.demo.contract.api.provider;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.demo.controller.MovieController;
import com.example.demo.domain.Movie;
import com.example.demo.service.interfaces.MovieService;

import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactBroker;
import au.com.dius.pact.provider.junitsupport.loader.PactBrokerAuth;
import au.com.dius.pact.provider.spring.junit5.MockMvcTestTarget;

@PactBroker(url = "${PACT_BROKER_BASE_URL}",
authentication = @PactBrokerAuth(token = "${PACT_BROKER_TOKEN}"))
@Provider("MoviesAppBack")
@ExtendWith(MockitoExtension.class)
public class MovieAppProviderTest {
	
	@Mock
	private MovieService movieService;
	
	@InjectMocks
	private MovieController movieController;
	
	@TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    public void pactVerificationTestTemplate(PactVerificationContext context) {
        context.verifyInteraction();
    }
	
	@BeforeEach
	public void changeContext(PactVerificationContext context) {
        MockMvcTestTarget testTarget = new MockMvcTestTarget();
        testTarget.setControllers(movieController);
        context.setTarget(testTarget);
    }
	
	@State("register a movie")
	public void registerMovie() {
		Movie movie = new Movie();
		movie.setTitle("El Padrino");
		movie.setDescription("Mafia movie");
		movie.setDirector("Francis Ford Coppola");
		
		Mockito.when(movieService.save(Mockito.any(Movie.class))).thenReturn(movie);
	}
	
	@State("list movies")
	public void listMovie() {
		Movie movie = new Movie();
		movie.setTitle("Batman: El caballero de la noche");
		movie.setDescription("Crime movie");
		movie.setDirector("Christopher Nolan");
		
		ArrayList<Movie> movies = new ArrayList<Movie>();
		movies.add(movie);
		
		Mockito.when(movieService.getAll()).thenReturn(movies);
	}
	
	@State("delete a movie")
	public void deleteMovie() {
		String title = "Cars";
		Mockito.doAnswer((i) -> {
			assertEquals(title, i.getArgument(0));
			return null;
		}).when(movieService).delete(title);
	}

}
