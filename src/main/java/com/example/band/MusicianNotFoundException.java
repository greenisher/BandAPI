package com.example.band;

public class MusicianNotFoundException extends Throwable {

    MusicianNotFoundException(Long id) {
        super("Can't find musician");
    }
}
