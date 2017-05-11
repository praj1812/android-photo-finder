package com.example.android.finalproject.ui;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.finalproject.R;

import java.io.File;
import java.util.ArrayList;

import static com.example.android.finalproject.R.id.swipeRefreshLayout;

/**
 * Fragment to display images from device's local favorites folder
 * <p>
 * Created by prajakti on 4/25/2017.
 */

public class FavoritesTab extends Fragment {
    private ArrayList<Uri> mImages = new ArrayList<>();
    private FavoritesItemAdapter mFavoritesItemAdapter;

    File[] allFiles;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             final Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.favorites_fragment, container, false);

        RecyclerView mRecyclerView
                = (RecyclerView) rootView.findViewById(R.id.favoritesRecyclerView);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mFavoritesItemAdapter = new FavoritesItemAdapter(mImages);
        mRecyclerView.setAdapter(mFavoritesItemAdapter);

        File folder = new File(Environment.getExternalStorageDirectory().getPath() + "/Favorites/");
        allFiles = folder.listFiles();

        // scan each image file in the favorites folder and display it
        if (allFiles != null) {
            System.out.println("scanning: " + allFiles.toString());
            boolean foundFile = false;
            for (File file : allFiles) {
                foundFile = true;
                System.out.println(foundFile);
                System.out.println("each file: " + file.toString());
                new SingleMediaScanner(getActivity().getApplicationContext(), file);
            }
        }

        return rootView;
    }

    /**
     * Scan each image file and display in the RecyclerView
     * source: http://stackoverflow.com/questions/13418807/how-can-i-display-images-from-a-specific-folder-on-android-gallery
     */
    public class SingleMediaScanner implements MediaScannerConnection.MediaScannerConnectionClient {
        private MediaScannerConnection mMs;
        private File mFile;

        public SingleMediaScanner(Context context, File f) {
            mFile = f;
            mMs = new MediaScannerConnection(context, this);
            mMs.connect();
        }

        public void onMediaScannerConnected() {
            mMs.scanFile(mFile.getAbsolutePath(), null);
        }

        public void onScanCompleted(String path, Uri uri) {
            mImages.add(uri);
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mFavoritesItemAdapter.notifyDataSetChanged();
                }
            });
            mMs.disconnect();
        }
    }
}