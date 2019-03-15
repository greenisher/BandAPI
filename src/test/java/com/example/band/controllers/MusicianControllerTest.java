package com.example.band.controllers;

import com.example.band.models.Musician;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.LinkedHashMap;
import java.util.List;

public class MusicianControllerTest {

    public static final String REST_SERVICE_URI = "http://localhost:8080/";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private MusicianController musicianController;

    @Test
    @SuppressWarnings("unchecked")
    private static void listAllMusicians(){
        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> musiciansMap = restTemplate.getForObject(REST_SERVICE_URI + "/musicians/", List.class);

        if(musiciansMap!=null){
            for(LinkedHashMap<String, Object> map : musiciansMap){
                System.out.println("Musician: id="+map.get("id")+", Band Name="+map.get("BandName"));
            }
        }else {
            System.out.println("Musicians do not exist.");
        }

    }


    @Test
    private static void getOne(){
        RestTemplate restTemplate = new RestTemplate();
        Musician musician = restTemplate.getForObject(REST_SERVICE_URI + "musicians/1", Musician.class);
        System.out.println(musician);
    }

    @Test
    public static void newMusician() {
        RestTemplate restTemplate = new RestTemplate();
        Musician musician = new Musician("Tegan and Sara", 7, true);
        URI uri = restTemplate.postForLocation(REST_SERVICE_URI + "/musicians", musician, Musician.class);
        System.out.println("New Band: " + uri.toASCIIString());
    }

    @Test
    public static void replaceMusician() {
        RestTemplate restTemplate = new RestTemplate();
        Musician musician = new Musician("Bruce Springsteen", 18, true);
        System.out.println(musician);
    }

    @Test
    public static void deleteMusician() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(REST_SERVICE_URI + "/musicians/1");
    }

    public static void main(String[] args){
        listAllMusicians();
        getOne();
        newMusician();
        listAllMusicians();
        replaceMusician();
        listAllMusicians();
        deleteMusician();
        listAllMusicians();
    }

}