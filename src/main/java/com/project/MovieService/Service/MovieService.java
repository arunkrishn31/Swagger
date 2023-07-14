package com.project.MovieService.Service;

import com.project.MovieService.Exception.NotFoundException;
import com.project.MovieService.Model.Movie;
import com.project.MovieService.Repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.MovieService.Exception.InvalidDataException;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    public Movie createMovie(Movie movie){
        if(movie==null)
            throw new InvalidDataException("Invalid Movie");
        return movieRepository.save(movie);
    }

    public Movie getMovie(Long id){
       return movieRepository.findById(id).orElseThrow(()-> new NotFoundException("Movie Not Found with id:"+id));
    }

    public List<Movie>getallMovies(){
        return movieRepository.findAll();
    }

    public Movie updateMovie(Long id, Movie movie){
        if(movie==null||id==null)
            throw new InvalidDataException("Invalid Movie");
        if(movieRepository.existsById(movie.getId())){
            Movie updateMovie=movieRepository.getReferenceById(id);
            updateMovie.setId(movie.getId());
            updateMovie.setName(movie.getName());
            updateMovie.setDirector(movie.getDirector());
            updateMovie.setActors(movie.getActors());
           return movieRepository.save(updateMovie);
        }else {
            throw  new NotFoundException("Movie Not Found with id: "+id);
        }
    }

    public void deleteMovie(Long id){
        if(movieRepository.existsById(id)){
             movieRepository.deleteById(id);
        }else {
            throw  new NotFoundException("Movie Not Found with id: "+id);
        }
    }
}
