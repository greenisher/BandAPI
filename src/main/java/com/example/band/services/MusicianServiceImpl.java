package com.example.band.services;

import com.example.band.models.Musician;
import com.example.band.repositories.MusicianRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class MusicianServiceImpl implements MusicianService {
    @Autowired
    MusicianRepository musicianRepository;

    public List<Musician> findAllMusicians() {
        return musicianRepository.findAll();
    }

    public Musician findMusician(Long id) {
        return musicianRepository.getOne(id);
    }

    public Musician updateMusician(Musician musician) {
        return musicianRepository.save(musician);
    }

    public void saveMusician(Musician musician) {
        musicianRepository.save(musician);
    }

    @Override
    public void deleteMusician(Musician musician) {
        musicianRepository.delete(musician);
    }

    @Override
    public boolean doesMusicianExist(Musician musician) {
        return findMusician(musician.getId())!=null;
    }
}
