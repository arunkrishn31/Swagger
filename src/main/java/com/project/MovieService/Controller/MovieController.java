package com.project.MovieService.Controller;

import com.project.MovieService.Model.Movie;
import com.project.MovieService.Service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Slf4j
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovie(@PathVariable Long id){
        Movie getMovie=movieService.getMovie(id);
        log.info("Get Movie with Id: {}",getMovie.getId());
        return ResponseEntity.ok(getMovie);
    }

    @PostMapping
    public ResponseEntity<Movie> createmovie(@RequestBody Movie movie){
        Movie createMovie=movieService.createMovie(movie);
        log.info("Created Movie with Id: {}",createMovie.getId());
        return ResponseEntity.ok(createMovie);
    }
    @GetMapping("/allMovies")
    public ResponseEntity<List<Movie>>getAllMovies(){
    List<Movie> movie =movieService.getallMovies();
    log.info("get All movies");
    return ResponseEntity.ok(movie);
    }

    @PutMapping("/{id}")
    public void updateMovie(@PathVariable Long id,@RequestBody Movie movie){
         movieService.updateMovie(id,movie);
        log.info("Updated a Movie with Id: {}",id);
    }

    @DeleteMapping("/{id}")
    public void delteMovie(@PathVariable Long id){
        movieService.deleteMovie(id);
        log.info("Delete a Movie with Id: {}",id);
    }
}
