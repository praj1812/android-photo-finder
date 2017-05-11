package com.example.android.finalproject.data;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.example.android.finalproject.VisualRecognitionClassification.BluemixApi;
import com.example.android.finalproject.VisualRecognitionClassification.ClassScore;
import com.example.android.finalproject.VisualRecognitionClassification.Classifier;
import com.example.android.finalproject.VisualRecognitionClassification.TaggedImage;
import com.example.android.finalproject.VisualRecognitionClassification.TaggedImages;
import com.google.gson.Gson;
import com.ibm.watson.developer_cloud.visual_recognition.v3.VisualRecognition;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.ClassifyImagesOptions;
import com.ibm.watson.developer_cloud.visual_recognition.v3.model.VisualClassification;

import java.util.ArrayList;

import static com.example.android.finalproject.ui.ExploreTab.mReference;

/**
 * Created by prajakti on 4/23/2017.
 */

public class ClassifyImage {
    private static VisualRecognition service;
    private static Uri currentImageUri;

    /**
     * Analyzes image using the Bluemix API and uploads to the Realtime Database as an ImagePost
     *
     * @param imageUri of image to classify
     * @param context
     */
    public static void findConcepts(Uri imageUri, Context context) {
        System.out.println(imageUri);
        ClassifyImagesOptions options;

        service = new VisualRecognition(VisualRecognition.VERSION_DATE_2016_05_20);
        service.setApiKey(BluemixApi.getKey());
        options = new ClassifyImagesOptions.Builder().url(imageUri.toString()).build();
        currentImageUri = imageUri;

        //since findConcepts is static
        ClassifyImage classifyImage = new ClassifyImage();
        AsyncClassify task = classifyImage.new AsyncClassify(context);

        task.execute(options);
    }

    /**
     * Classifies image and uploads to database as an ImagePost with the image's uri and the
     * image's concepts
     */
    public class AsyncClassify extends AsyncTask<ClassifyImagesOptions, ArrayList<String>, VisualClassification> {
        Context context;

        public AsyncClassify(Context context) {
            this.context = context;
        }

        @Override
        protected VisualClassification doInBackground(ClassifyImagesOptions... params) {
            VisualClassification result = service.classify(params[0]).execute();
            return result;
        }

        @Override
        protected void onPostExecute(VisualClassification visualClassification) {
            ArrayList<String> conceptList = getConcepts(visualClassification);
            String key = mReference.push().getKey();

            ImagePost imagePost = new ImagePost(currentImageUri.toString(), conceptList);
            mReference.child(key).setValue(imagePost);
        }
    }

    public static ArrayList<String> getConcepts(VisualClassification visualClassification) {
        Gson gson = new Gson();
        System.out.println("visualClassification: " + visualClassification.toString());
        TaggedImages taggedImages = gson.fromJson(visualClassification.toString(), TaggedImages.class);

        TaggedImage taggedImage = taggedImages.getTaggedImagesList().get(0);
        Classifier classifier = taggedImage.getClassifiers().get(0);
        ArrayList<ClassScore> classScoreList = classifier.getClassScoresList();

        ArrayList<String> conceptList = new ArrayList<>();
        for (int i = 0; i < classScoreList.size(); i++) {
            conceptList.add(classScoreList.get(i).getImageClass().toLowerCase());
        }
        return conceptList;
    }
}