package com.example.band;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(MusicianRepository repository) {
        return args -> {
            log.info("Preloading " + repository.save(new Musician("The National", 7, Boolean.TRUE)));
            log.info("Preloading " + repository.save(new Musician("Lana Del Rey", 5, Boolean.TRUE)));
            log.info("Preloading " + repository.save(new Musician("Sleater Kinney", 8, Boolean.TRUE)));
            log.info("Preloading " + repository.save(new Musician("Bikini Kill", 3, Boolean.FALSE)));
            log.info("Preloading " + repository.save(new Musician("Smashing Pumpkins", 10, Boolean.FALSE)));
        };
    }
}
