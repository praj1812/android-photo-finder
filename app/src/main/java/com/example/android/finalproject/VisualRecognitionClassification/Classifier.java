package com.example.android.finalproject.VisualRecognitionClassification;

import java.util.ArrayList;

/**
 * Created by prajakti on 4/24/2017.
 */

public class Classifier {
    private ArrayList<ClassScore> classes;
    private String classifier_id;
    private String name;

    public ArrayList<ClassScore> getClassScoresList() {
        return classes;
    }

    public String getClassifierId() {
        return classifier_id;
    }

    public String getName() {
        return name;
    }
}
