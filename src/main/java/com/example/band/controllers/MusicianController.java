package com.example.band.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.band.exceptions.MusicianConflictError;
import com.example.band.exceptions.MusicianNotFoundException;
import com.example.band.repositories.MusicianRepository;
import com.example.band.resources.MusicianResourceAssembler;
import com.example.band.models.Musician;
import com.example.band.services.MusicianService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
public class MusicianController {

    private final MusicianRepository repository;

    private final MusicianResourceAssembler assembler;

    MusicianController(MusicianRepository repository, MusicianResourceAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @Autowired
    MusicianService musicianService;

    @GetMapping("/musicians")
    public ResponseEntity<List<Musician>> listAllMusicians() {
        List<Musician> musicians = musicianService.findAllMusicians();
        if(musicians.isEmpty()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Musician>>(musicians, HttpStatus.OK);
/*    public Resources<Resource<Musician>> all() {
        List<Resource<Musician>> musicians = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(musicians,
                linkTo(methodOn(MusicianController.class).all()).withSelfRel());*/
    }

    @GetMapping("/musicians/{id}")
    public ResponseEntity<?> one(@PathVariable("id") long id) {
        Musician musician = musicianService.findMusician(id);
        if(musician == null) {
            return new ResponseEntity(new MusicianNotFoundException(id), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Musician>(musician, HttpStatus.OK);
/*    public Resource<Musician> one(@PathVariable Long id) throws MusicianNotFoundException {

        Musician musician = repository.findById(id)
                .orElseThrow(() -> new MusicianNotFoundException(id));

        return assembler.toResource(musician);*/
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
/*    public ResponseEntity<?> newMusician(@RequestBody Musician newMusician) throws URISyntaxException {

        Resource<Musician> resource = assembler.toResource(repository.save(newMusician));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);*/
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
    /*ResponseEntity<?> replaceMusician(@RequestBody Musician newMusician, @PathVariable Long id) throws URISyntaxException {

        Musician updatedMusician = repository.findById(id)
                .map(musician -> {
                    musician.setBandName(newMusician.getBandName());
                    musician.setAlbums(newMusician.getAlbums());
                    musician.setActive(newMusician.getActive());
                    return repository.save(musician);
                })
                .orElseGet(() -> {
                    newMusician.setId(id);
                    return repository.save(newMusician);
                });

        Resource<Musician> resource = assembler.toResource(updatedMusician);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);*/
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
