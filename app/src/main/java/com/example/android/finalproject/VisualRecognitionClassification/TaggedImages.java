package com.example.android.finalproject.VisualRecognitionClassification;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by prajakti on 4/24/2017.
 */

public class TaggedImages {
    @SerializedName("images")
    private ArrayList<TaggedImage> taggedImagesList;
    private int images_processed;

    public ArrayList<TaggedImage> getTaggedImagesList() {
        return taggedImagesList;
    }

    public int getImagesProcessed() {
        return images_processed;
    }
}
