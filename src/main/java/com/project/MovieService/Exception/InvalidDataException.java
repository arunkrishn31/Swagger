package com.project.MovieService.Exception;

public class InvalidDataException extends RuntimeException {
    public InvalidDataException(String msg){
         super(msg);
    }
}
