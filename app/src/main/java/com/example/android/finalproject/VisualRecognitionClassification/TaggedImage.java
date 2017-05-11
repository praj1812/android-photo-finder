package com.example.android.finalproject.VisualRecognitionClassification;

import com.example.android.finalproject.VisualRecognitionClassification.Classifier;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prajakti on 4/24/2017.
 */

public class TaggedImage {
    private ArrayList<Classifier> classifiers;
    @SerializedName("image")
    private String imageName;
    private String resolved_url;
    private String source_url;

    public ArrayList<Classifier> getClassifiers() {
        return classifiers;
    }

    public String getImageName() {
        return imageName;
    }

    public String getResolved_url() {
        return resolved_url;
    }

    public String getSource_url() {
        return source_url;
    }
}
