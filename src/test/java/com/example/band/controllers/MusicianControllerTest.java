package com.example.band.controllers;


import com.example.band.models.Musician;
import com.example.band.services.MusicianService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class MusicianControllerTest {

    MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    MusicianController musicianController;

    @MockBean
    MusicianService musicianService;

    private List<Musician> musicians;

    @Before
    public void setup() throws Exception {
        this.mockMvc = standaloneSetup(this.musicianController).build();
        Musician musician1 = Musician.builder()
                .bandName("Aja")
                .albums(1)
                .active(true)
                .build();
        Musician musician2 = Musician.builder()
                .bandName("apogee")
                .albums(0)
                .active(true)
                .build();

        musicians = new ArrayList<>();
        musicians.add(musician1);
        musicians.add(musician2);
    }

    @Test
    public void testMusicians() throws Exception {
        when(musicianService.findMusician(any(Long.class))).thenReturn((Musician) musicians);

        mockMvc.perform(get("/musicians/").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}