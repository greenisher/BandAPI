package com.example.band;

import org.springframework.data.jpa.repository.JpaRepository;

interface MusicianRepository extends JpaRepository<Musician, Long> {
}
