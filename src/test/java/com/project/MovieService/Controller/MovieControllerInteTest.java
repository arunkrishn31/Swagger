package com.project.MovieService.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.MovieService.Model.Movie;
import com.project.MovieService.Repository.MovieRepository;
import com.project.MovieService.Service.MovieService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MovieControllerInteTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    MovieRepository movieRepository;

    @Autowired
    MovieService movieService;
//    @BeforeEach
//    void clean(){
//        movieRepository.deleteAllInBatch();
//    }

    @Test
    void givenMovie_whencreateMovie_thnreturnsavedMovie()throws Exception{
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("S S Rajamouli");
        movie.setActors(Arrays.asList("NTR","Ram Charan","AliaBhat","AjayDevagan"));

        var Response=mockMvc.perform(post("/movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        Response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect((jsonPath("$.actors", is(movie.getActors()))));
    }

    @Test
    void givenMovieId_whnfetchMovie_thenreturnMovie()throws Exception{
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("S S Rajamouli");
        movie.setActors(Arrays.asList("NTR","Ram Charan","AliaBhat","AjayDevagan"));

       Movie savedMovie=movieRepository.save(movie);

       var Response=mockMvc.perform(get("/movies/"+savedMovie.getId()));
        Response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(savedMovie.getId().intValue())))
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect((jsonPath("$.actors", is(movie.getActors()))));

    }

    @Test
    void givenMovieId_whnfetchMovie_thenreturnallMovies()throws Exception{
        Movie movie1=new Movie();
        movie1.setName("RRR");
        movie1.setDirector("S S Rajamouli");
        movie1.setActors(Arrays.asList("NTR","Ram Charan","AliaBhat","AjayDevagan"));
        Movie savedMovie1=movieRepository.save(movie1);

        Movie movie2=new Movie();
        movie2.setName("Sye");
        movie2.setDirector("S S Rajamouli");
        movie2.setActors(Arrays.asList("Nithin","Geniliya","Ashok","Rajiv"));
        Movie savedMovie2=movieRepository.save(movie2);

        List<Movie> movies = Arrays.asList(savedMovie1, savedMovie2);

            when(movieRepository.findAll()).thenReturn(movies);

        this.mockMvc.perform(get("/allMovies"))
                .andExpect(status().isOk())
                .andExpect(view().name("Movies-list"))
                .andExpect(model().attribute("Movies", movies))
                .andExpect(model().attribute("Movies", Matchers.hasSize(3)))
                .andDo(print());
    }

    @Test
    void givenMovie_whencreateMovie_thnreturnsupdatedMovie()throws Exception{
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("S S Rajamouli");
        movie.setActors(Arrays.asList("NTR","Ram Charan","AliaBhat"));

        Movie savedMovie=movieRepository.save(movie);
        Long id=savedMovie.getId();

        movie.setActors(List.of("NTR","Ram Charan","AliaBhat","AjayDevagan"));

        var Response=mockMvc.perform(put("/movies/"+id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(movie)));

        Response.andDo(print())
                .andExpect(status().isOk());

        var fetchResponse= mockMvc.perform(get("/movies/"+id));
        fetchResponse.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(movie.getName())))
                .andExpect(jsonPath("$.director", is(movie.getDirector())))
                .andExpect((jsonPath("$.actors", is(movie.getActors()))));

    }

    @Test
    void givenMovie_whencreateMovie_thndeleteMovie()throws Exception {
        Movie movie=new Movie();
        movie.setName("RRR");
        movie.setDirector("S S Rajamouli");
        movie.setActors(Arrays.asList("NTR","Ram Charan","AliaBhat","AjayDevagan"));

        Movie savedMovie=movieRepository.save(movie);
        Long id=savedMovie.getId();

        mockMvc.perform(delete("/movies/"+id))
                .andDo(print())
                .andExpect(status().isOk());
        assertFalse(movieRepository.findById(id).isPresent());

    }


}