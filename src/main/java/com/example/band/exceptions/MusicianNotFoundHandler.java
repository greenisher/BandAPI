package com.example.band.exceptions;

import com.example.band.exceptions.MusicianNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class MusicianNotFoundHandler {

    @ResponseBody
    @ExceptionHandler(MusicianNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String musicianNotFoundHandler(MusicianNotFoundException ex) {return ex.getMessage();}
}
