package com.example.band;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Musician {

    public Musician() {

    }

    private @Id @GeneratedValue Long id;
    private String bandName;
    private Integer albums;
    private Boolean active;

    Musician(String bandName, Integer albums, Boolean active) {
        this.bandName = bandName;
        this.albums = albums;
        this.active = active;
    }

}
