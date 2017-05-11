package com.example.android.finalproject;

import com.example.android.finalproject.data.ImagePost;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;

/**
 * Created by prajakti on 5/2/2017.
 */

public class ImagePostTest {
    ImagePost imagePost;

    @Before
    public void setup() {
        String url = "someurl";

        ArrayList<String> conceptTagsList = new ArrayList<>();
        conceptTagsList.add("concept1");
        conceptTagsList.add("concept2");
        conceptTagsList.add("concept3");

        imagePost = new ImagePost(url, conceptTagsList);
    }

    @Test
    public void testGetConceptsTagsList() {
        assertEquals(3, imagePost.getConceptTagsList().size());
    }

    @Test
    public void testGetUrl() {
        assertEquals("someurl", imagePost.getUrl());
    }
}
