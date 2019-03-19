package com.example.band.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;

@ControllerAdvice
public class MusicianNotFoundHandlerTest {

    @ExceptionHandler(MusicianNotFoundException.class)
    public ResponseEntity<MusicianNotFoundException> MusicianNotFoundHandler(@PathVariable("id") long id) {
        MusicianNotFoundException error = new MusicianNotFoundException(id);
        return new ResponseEntity<MusicianNotFoundException>(error, HttpStatus.NOT_FOUND);
    }
}