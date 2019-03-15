package com.example.band.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import com.example.band.exceptions.MusicianNotFoundException;
import com.example.band.repositories.MusicianRepository;
import com.example.band.resources.MusicianResourceAssembler;
import com.example.band.models.Musician;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/musicians")
    public Resources<Resource<Musician>> all() {
        List<Resource<Musician>> musicians = repository.findAll().stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(musicians,
                linkTo(methodOn(MusicianController.class).all()).withSelfRel());
    }

    @PostMapping("/musicians")
    ResponseEntity<?> newMusician(@RequestBody Musician newMusician) throws URISyntaxException {

        Resource<Musician> resource = assembler.toResource(repository.save(newMusician));

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @GetMapping("/musicians/{id}")
    public Resource<Musician> one(@PathVariable Long id) throws MusicianNotFoundException {

        Musician musician = repository.findById(id)
                .orElseThrow(() -> new MusicianNotFoundException(id));

        return assembler.toResource(musician);
    }

    @PutMapping("/musicians/{id}")
    ResponseEntity<?> replaceMusician(@RequestBody Musician newMusician, @PathVariable Long id) throws URISyntaxException {

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
                .body(resource);
    }

    @DeleteMapping("/musicians/{id}")
    ResponseEntity<?> deleteMusician(@PathVariable Long id) {
        repository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

}
