package com.example.band.exceptions;

public class MusicianNotFoundException extends Throwable {

    public MusicianNotFoundException(Long id) {
        super("Can't find musician");
    }
}
