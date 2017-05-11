package com.example.android.finalproject;

import com.example.android.finalproject.VisualRecognitionClassification.ClassScore;
import com.example.android.finalproject.VisualRecognitionClassification.Classifier;
import com.example.android.finalproject.VisualRecognitionClassification.TaggedImages;
import com.google.gson.Gson;

import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.assertEquals;

/**
 * Created by prajakti on 5/2/2017.
 */

public class TestTaggedImageParsing {
    static TaggedImages taggedImages;

    @BeforeClass
    public static void parseJson() {
        String visualClassificationJson = VisualClassificationJson.getJsonString();
        Gson gson = new Gson();
        taggedImages = gson.fromJson(visualClassificationJson, TaggedImages.class);
    }

    @Test
    public void testTaggedImages() {
        assertEquals(1, taggedImages.getTaggedImagesList().size());
        assertEquals(1, taggedImages.getImagesProcessed());
    }

    @Test
    public void testTaggedImage() {
        ArrayList<Classifier> classifiers = taggedImages.getTaggedImagesList().get(0)
                .getClassifiers();
        assertEquals(1, classifiers.size());
    }

    @Test
    public void testClassifier() {
        ArrayList<ClassScore> classScoreList = taggedImages.getTaggedImagesList().get(0)
                .getClassifiers().get(0).getClassScoresList();
        assertEquals(10, classScoreList.size());
    }

    @Test
    public void testClassScore() {
        ArrayList<ClassScore> classScoreList = taggedImages.getTaggedImagesList().get(0)
                .getClassifiers().get(0).getClassScoresList();

        assertEquals("curved road", classScoreList.get(0).getImageClass());
        assertEquals("path near canal/river", classScoreList.get(1).getImageClass());
        assertEquals("greencolor", classScoreList.get(8).getImageClass());
        assertEquals("greenishness color", classScoreList.get(9).getImageClass());
    }
}
