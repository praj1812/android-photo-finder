package com.example.android.finalproject;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;

import com.example.android.finalproject.VisualRecognitionClassification.BluemixApi;
import com.example.android.finalproject.data.ClassifyImage;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by prajakti on 5/2/2017.
 */

public class ClassifyImageInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.android.finalproject", appContext.getPackageName());
    }

    @Test
    public void testFindConceptsPlant() {
        Uri plantImageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/my-awesome-project-e15e0.appspot.com/o/explore_item%2Fimage%3A15335?alt=media&token=015b02c3-a199-469c-9f63-8b6cc92707c7");
        VisualClassification result = getVisualClassification(plantImageUri);
        ArrayList<String> conceptsList = ClassifyImage.getConcepts(result);

        System.out.print("conceptsList: " + conceptsList.toString());
        assertNotNull(conceptsList);
    }

    @Test
    public void testFindConceptsPanda() {
        Uri pandaImageUri = Uri.parse("https://firebasestorage.googleapis.com/v0/b/my-awesome-project-e15e0.appspot.com/o/explore_item%2Fimage%3A51?alt=media&token=6f01f8b8-666d-4edf-be1d-e8b38baf0b7b");
        VisualClassification result = getVisualClassification(pandaImageUri);
        ArrayList<String> conceptsList = ClassifyImage.getConcepts(result);

        System.out.print("conceptsList: " + conceptsList.toString());
        assertNotNull(conceptsList);
    }

    /**
     * Analyzes image using the Bluemix API
     *
     * @param imageUri of image to classify
     */
    public static VisualClassification getVisualClassification(Uri imageUri) {
        VisualRecognition service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(BluemixApi.getKey());
        ClassifyImagesOptions options =
                new ClassifyImagesOptions.Builder().url(imageUri.toString()).build();

        return service.classify(options).execute();
    }
}
