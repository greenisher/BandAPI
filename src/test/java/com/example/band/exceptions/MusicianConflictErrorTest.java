package com.example.band.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MusicianConflictErrorTest {

    @ExceptionHandler(MusicianConflictError.class)
    public ResponseEntity<MusicianConflictError> conflictHandler(Exception ex) {
        MusicianConflictError error = new MusicianConflictError("Musician exists");
        return new ResponseEntity<MusicianConflictError>(error, HttpStatus.CONFLICT);
    }
}