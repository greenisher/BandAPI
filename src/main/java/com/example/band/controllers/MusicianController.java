package com.example.band.controllers;

import com.example.band.exceptions.MusicianConflictError;
import com.example.band.exceptions.MusicianNotFoundException;
import com.example.band.models.Musician;
import com.example.band.services.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
public class MusicianController {

    @Autowired
    MusicianService musicianService;

    @GetMapping("/musicians")
    public ResponseEntity<List<Musician>> listAllMusicians() {
        List<Musician> musicians = musicianService.findAllMusicians();
        if(musicians.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Musician>>(musicians, HttpStatus.OK);
    }

    @GetMapping("/musicians/{id}")
    public ResponseEntity<?> one(@PathVariable("id") long id) {
        Musician musician = musicianService.findMusician(id);
        if(musician == null) {
            return new ResponseEntity(new MusicianNotFoundException(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Musician>(musician, HttpStatus.OK);
    }

    @PostMapping("/musicians")
    public ResponseEntity<?> newMusician(@RequestBody Musician musician, UriComponentsBuilder ucBuilder) {
        if(musicianService.doesMusicianExist(musician)) {
            return new ResponseEntity(new MusicianConflictError("Musician already exists"), HttpStatus.CONFLICT);
        }
        musicianService.saveMusician(musician);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/musicians/{id}").buildAndExpand(musician.getId()).toUri());
        return new ResponseEntity<String>(headers, HttpStatus.CREATED);
    }


    @PutMapping("/musicians/{id}")
    public ResponseEntity<?> replaceMusician(@PathVariable("id") long id, @RequestBody Musician musician) {
        Musician updatedMusician = musicianService.findMusician(id);

        if(updatedMusician == null) {
            return new ResponseEntity(new MusicianNotFoundException(id), HttpStatus.NOT_FOUND);
        }

        updatedMusician.setBandName(musician.getBandName());
        updatedMusician.setAlbums(musician.getAlbums());
        updatedMusician.setActive(musician.getActive());

        musicianService.updateMusician(updatedMusician);
        return new ResponseEntity<Musician>(updatedMusician, HttpStatus.OK);
    }

    @DeleteMapping("/musicians/{id}")
    public ResponseEntity<?> deleteMusician(@PathVariable("id") Long id) {
        Musician musician = musicianService.findMusician(id);
        if(musician == null) {
            return new ResponseEntity(new MusicianNotFoundException(id), HttpStatus.NOT_FOUND);
        }

        musicianService.deleteMusician(musician);

        return new ResponseEntity<Musician>(HttpStatus.NO_CONTENT);
    }

}
