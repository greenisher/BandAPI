package com.example.band.services;

import com.example.band.models.Musician;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("musicianService")
public interface MusicianService {

    List<Musician> findAllMusicians();

    Musician findMusician(Long id);

    Musician updateMusician(Musician musician);

    void saveMusician(Musician musician);

    void deleteMusician(Musician musician);

    boolean doesMusicianExist(Musician musician);

}
