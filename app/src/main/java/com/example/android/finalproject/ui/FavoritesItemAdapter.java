package com.example.android.finalproject.ui;

import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Adapter for FavoritesTab's RecyclerView
 * <p>
 * Created by prajakti on 4/25/2017.
 */

public class FavoritesItemAdapter extends RecyclerView.Adapter<FavoritesItemAdapter.ViewHolder> {
    private ArrayList<Uri> images;

    FavoritesItemAdapter(ArrayList<Uri> images) {
        this.images = images;
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public Button wallpaperButton;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.favorites_image_view);
            wallpaperButton = (Button) itemView.findViewById(R.id.wallpaper_button);
        }
    }

    @Override
    public FavoritesItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View imageListItem = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorites_item, parent, false);
        return new FavoritesItemAdapter.ViewHolder(imageListItem);
    }

    @Override
    public void onBindViewHolder(final FavoritesItemAdapter.ViewHolder holder, final int position) {
        final Uri imageUri = images.get(position);
        Glide.with(holder.imageView.getContext()).load(imageUri).centerCrop().into(holder.imageView);

        holder.wallpaperButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncGettingBitmapFromUrl(v.getContext()).execute(imageUri.toString());
            }
        });
    }

    public class AsyncGettingBitmapFromUrl extends AsyncTask<String, Void, Bitmap> {
        Context context;

        public AsyncGettingBitmapFromUrl(Context context) {
            this.context = context;
        }

        Bitmap mBitmap = null;

        // Converts the image from uri String to Bitmap
        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                mBitmap = Glide.with(context).load(params[0]).asBitmap().
                        into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            return mBitmap;
        }

        // Sets selected image as wallpaper
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            WallpaperManager myWallpaperManager = WallpaperManager.getInstance(context);
            try {
                myWallpaperManager.setBitmap(bitmap);
                Toast.makeText(context, "Set as wallpaper", Toast.LENGTH_LONG).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}