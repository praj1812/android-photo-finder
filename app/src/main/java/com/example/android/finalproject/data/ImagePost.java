package com.example.android.finalproject.data;

import java.util.ArrayList;

/**
 * Firebase Realtime Database data representation
 * <p>
 * Created by prajakti on 4/23/2017.
 */

public class ImagePost {
    public String url;
    public ArrayList<String> conceptTagsList;

    public ImagePost(String url, ArrayList<String> conceptTagsList) {
        this.url = url;
        this.conceptTagsList = conceptTagsList;
    }

    public String getUrl() {
        return url;
    }

    public ArrayList<String> getConceptTagsList() {
        return conceptTagsList;
    }
}
