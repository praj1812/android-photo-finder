package com.example.android.finalproject.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.android.finalproject.R;

import java.util.ArrayList;

/**
 * Activity that displays when user searches for a concept
 * <p>
 * Created by prajakti on 4/30/2017.
 */

public class SearchActivity extends AppCompatActivity {

    private ArrayList<Uri> mImages = new ArrayList<>();
    private ExploreItemAdapter mExploreItemAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        Intent intent = getIntent();
        ArrayList<String> searchResultUriList = intent.getStringArrayListExtra("searchResultUriList");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recyclerViewSearchActivity);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mExploreItemAdapter = new ExploreItemAdapter(mImages);
        mRecyclerView.setAdapter(mExploreItemAdapter);

        for (String searchResultUri : searchResultUriList) {
            mImages.add(Uri.parse(searchResultUri));
            mExploreItemAdapter.notifyDataSetChanged();
        }
    }
}