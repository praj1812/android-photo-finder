package com.example.android.finalproject.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.example.android.finalproject.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Adapter for ExploreTab's RecyclerView
 * Also saves the image when the button in the holder is clicked
 * <p>
 * Created by prajakti on 4/8/2017.
 */

public class ExploreItemAdapter extends RecyclerView.Adapter<ExploreItemAdapter.ViewHolder> {
    private ArrayList<Uri> images;

    ExploreItemAdapter(ArrayList<Uri> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public View view;
        public ImageView imageView;
        public Button favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view);
            view = itemView;
            favoriteButton = (Button) itemView.findViewById(R.id.favorites_button);
        }
    }

    @Override
    public ExploreItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View imageListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.explore_item, parent, false);

        return new ViewHolder(imageListItem);
    }

    @Override
    public void onBindViewHolder(final ExploreItemAdapter.ViewHolder holder, final int position) {
        final Uri imageUri = images.get(position);
        Glide.with(holder.imageView.getContext()).load(imageUri).centerCrop().into(holder.imageView);

        holder.favoriteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncGetBitmapFromUrl(holder.imageView.getContext())
                        .execute(imageUri.toString());
            }
        });
    }

    /**
     * Converts image uri to a Bitmap which is then saved in the device's favorites folder
     * (which either exists or is created here)
     */
    public class AsyncGetBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
        Context context;

        public AsyncGetBitmapFromUrl(Context context) {
            this.context = context;
        }

        Bitmap mBitmap = null;

        // Loads the image from the uri
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                mBitmap = Glide.with(context).load(params[0]).asBitmap()
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        // source - http://stackoverflow.com/questions/22781430/android-saving-bitmap-image-temporarily
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            File newFile = new File(Environment.getExternalStorageDirectory() + "/Favorites/");
            if (!newFile.exists()) {
                newFile.mkdirs();
            }

            OutputStream outStream = null;
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(System.currentTimeMillis());
            File file = new File(Environment.getExternalStorageDirectory() + "/Favorites/"
                    + timeStamp + ".png");

            try {
                outStream = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.PNG, 85, outStream);
                outStream.close();
                Toast.makeText(context, "Added to Favourites", Toast.LENGTH_LONG).show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}