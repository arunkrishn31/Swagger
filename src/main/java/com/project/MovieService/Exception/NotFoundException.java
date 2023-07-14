package com.project.MovieService.Exception;

public class NotFoundException extends  RuntimeException{
   public NotFoundException(String msg){
        super(msg);
    }
}
