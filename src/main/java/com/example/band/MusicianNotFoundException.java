package com.example.band;

class MusicianNotFoundException extends RuntimeException {
    MusicianNotFoundException(Long id) {

        super("cannot find musician " + id);
    }
}
