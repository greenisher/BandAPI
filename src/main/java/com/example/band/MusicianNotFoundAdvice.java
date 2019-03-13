package com.example.band;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class MusicianNotFoundAdvice {

    @ResponseBody
    @ExceptionHandler(MusicianNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String musicianNotFoundHandler(MusicianNotFoundException ex) {return ex.getMessage();}
}
