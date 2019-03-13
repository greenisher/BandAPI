package com.example.band;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class MusicianResourceAssembler implements ResourceAssembler<Musician, Resource<Musician>>{

    @Override
    public Resource<Musician> toResource(Musician musician) {
        return new Resource<>(musician,
                linkTo(methodOn(MusicianController.class).one(musician.getId())).withSelfRel(),
                linkTo(methodOn(MusicianController.class).all()).withRel("musicians"));
    }
}
