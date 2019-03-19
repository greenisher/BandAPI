package com.example.band.resources;

import com.example.band.models.Musician;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class MusicianResourceAssemblerTest {

    @InjectMocks
    private MusicianResourceAssembler musicianResourceAssembler;

    @Before
    public void setup() {
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(new MockHttpServletRequest()));
    }

    @Test
    public void toResource() {
        Musician musician = new Musician();
        musician.setId(Long.valueOf(5));
        ResourceSupport resource = musicianResourceAssembler.toResource(musician);
        assertEquals(1,resource.getLinks().size());
        assertEquals("musicians",resource.getLinks().get(0).getRel());
        assertTrue(resource.getLinks().get(0).getHref().contains("musicians"));
    }
}