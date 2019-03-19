package com.example.band.exceptions;

public class MusicianConflictError extends Throwable {

    private String conflictMessage;

    public MusicianConflictError(String conflictMessage){
        this.conflictMessage = conflictMessage;
    }

    public String getConflictMessage() {
        return conflictMessage;
    }
}
