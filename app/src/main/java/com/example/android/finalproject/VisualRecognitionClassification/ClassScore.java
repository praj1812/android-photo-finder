package com.example.android.finalproject.VisualRecognitionClassification;

import com.google.gson.annotations.SerializedName;

/**
 * Created by prajakti on 4/24/2017.
 */

public class ClassScore {
    @SerializedName("class")
    private String imageClass;
    private double score;
    private String type_hierarchy;

    public String getImageClass() {
        return imageClass;
    }

    public double getScore() {
        return score;
    }

    public String getTypeHierarchy() {
        return type_hierarchy;
    }
}
