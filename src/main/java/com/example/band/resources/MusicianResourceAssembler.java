package com.example.band.resources;

import com.example.band.controllers.MusicianController;
import com.example.band.models.Musician;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component
public class MusicianResourceAssembler implements ResourceAssembler<Musician, Resource<Musician>>{

    @Override
    public Resource<Musician> toResource(Musician musician) {
        return new Resource<>(musician,
                linkTo(methodOn(MusicianController.class).one(musician.getId())).withSelfRel());
    }
}
