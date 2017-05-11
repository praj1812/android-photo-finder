package com.example.android.finalproject.data;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static com.example.android.finalproject.ui.ExploreTab.storage;

/**
 * Created by prajakti on 4/11/2017.
 */

public class CloudStorage {

    /**
     * Upload user selected image to cloud storage
     * Analyze this image to get concepts
     * Push this image url and its concepts to the Realtime Database
     * <p>
     * // source: https://firebase.google.com/docs/storage/android/upload-files
     *
     * @param imageUri user selected image Uri
     * @param context
     */
    public static void upload(final Uri imageUri, final Context context) {
        final StorageReference storageRef = storage.getReference();

        StorageMetadata metadata = new StorageMetadata.Builder().setContentType("image/jpeg")
                .build();

        UploadTask uploadTask = storageRef.child("explore_item/" + imageUri.getLastPathSegment())
                .putFile(imageUri, metadata);

        // Listen for state changes, errors, and completion of the upload.
        uploadTask.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot
                        .getTotalByteCount();
                System.out.println("Upload is " + progress + "% done");
            }
        }).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onPaused(UploadTask.TaskSnapshot taskSnapshot) {
                System.out.println("Upload is paused");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                Toast.makeText(context, "Unable to upload to storage", Toast.LENGTH_SHORT);
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                // Get Image's location on cloud storage as a uri and upload the uri to realtime database
                storageRef.child("explore_item/" + imageUri.getLastPathSegment())
                        .getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {

                    // analyze uploaded image for concepts and push to firebase
                    @Override
                    public void onSuccess(Uri uri) {
                        ClassifyImage.findConcepts(uri, context);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Toast.makeText(context, "Unable to share image", Toast.LENGTH_SHORT).show();
                        exception.printStackTrace();
                    }
                });
            }
        });
    }
}