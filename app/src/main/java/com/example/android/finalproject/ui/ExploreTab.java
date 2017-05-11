package com.example.android.finalproject.ui;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.finalproject.data.CloudStorage;
import com.example.android.finalproject.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;


/**
 * Fragment to display all shared images
 * Users can:
 *      Share images from their device gallery or camera
 *      'like' an image and add to a local favorites folder
 *      Search the shared images database by a concept query
 * <p>
 * Created by prajakti on 4/25/2017.
 */

public class ExploreTab extends Fragment {

    private ArrayList<Uri> mImages = new ArrayList<>();
    private ExploreItemAdapter mExploreItemAdapter;

    public static FirebaseStorage storage = FirebaseStorage.getInstance();
    public static DatabaseReference mReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.explore_fragment, container, false);
        setHasOptionsMenu(true);

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(
                new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mExploreItemAdapter = new ExploreItemAdapter(mImages);
        mRecyclerView.setAdapter(mExploreItemAdapter);

        mReference = FirebaseDatabase.getInstance().getReference();

        // Reads the data at Realtime database reference and loads into recycler view
        mReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                try {
                    HashMap hashMap = (HashMap) dataSnapshot.getValue();
                    Collection hashMapValues = hashMap.values();

                    for (Object value : hashMapValues) {
                        String uriString = (String) ((HashMap) value).get("url");
                        mImages.add(Uri.parse(uriString));
                        mExploreItemAdapter.notifyDataSetChanged();
                    }
                } catch (ClassCastException e) {
                    e.printStackTrace();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "The read failed: " + databaseError.getCode(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        // source: http://www.viralandroid.com/2016/02/android-floating-action-menu-example.html
        final FloatingActionMenu floatingActionMenu
                = (FloatingActionMenu) rootView.findViewById(R.id.floating_action_menu);
        floatingActionMenu.bringToFront();

        final FloatingActionButton floatingActionButtonCamera
                = (FloatingActionButton) rootView.findViewById(R.id.floating_action_camera);
        final FloatingActionButton floatingActionButtonGallery
                = (FloatingActionButton) rootView.findViewById(R.id.floating_action_gallery);

        // For selection of multiple images from device gallery
        floatingActionButtonGallery.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent();
                pictureIntent.setType("image/*");
                pictureIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                pictureIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(pictureIntent, "Select Picture"), 1);
            }
        });

        // Gets image using device camera
        floatingActionButtonCamera.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(pictureIntent, 0);
            }
        });
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent imageIntent) {
        super.onActivityResult(requestCode, resultCode, imageIntent);

        if (imageIntent != null) {
            // for single image upload
            if (imageIntent.getData() != null) {
                mImages.add(0, imageIntent.getData());
                mExploreItemAdapter.notifyDataSetChanged();
                CloudStorage.upload(imageIntent.getData(), getContext());

                // for multiple image upload where images are saved as ClipData and getData is null
                // source - http://stackoverflow.com/questions/19585815/select-multiple-images-from-android-gallery
            } else if (imageIntent.getClipData() != null) {
                ClipData clipData = imageIntent.getClipData();
                for (int i = 0; i < clipData.getItemCount(); i++) {
                    mImages.add(0, clipData.getItemAt(i).getUri());
                    mExploreItemAdapter.notifyDataSetChanged();
                    CloudStorage.upload(clipData.getItemAt(i).getUri(), getContext());
                }
            }
        }
    }

    // Creates SearchView in ActionBar, and takes user query
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.search_menu, menu);
        MenuItem searchViewItem = menu.findItem(R.id.action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchViewItem);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(final String query) {
                searchView.clearFocus();
                final String queryLower = query.toLowerCase();

                mReference.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ArrayList searchResultUriList = new ArrayList();
                        Collection hashMapValues = null;
                        try {
                            HashMap hashMap = (HashMap) dataSnapshot.getValue();
                            hashMapValues = hashMap.values();
                        } catch (ClassCastException e) {
                            e.printStackTrace();
                        }

                        for (Object value : hashMapValues) {
                            ArrayList<String> conceptTagsList
                                    = (ArrayList<String>) ((HashMap) value).get("conceptTagsList");
                            if (conceptTagsList.contains(queryLower)) {
                                String uriString = (String) ((HashMap) value).get("url");
                                searchResultUriList.add(uriString);
                                System.out.println("added uri: " + uriString);
                            }
                        }
                        if (searchResultUriList.size() == 0) {
                            System.out.println(searchResultUriList);
                            Toast.makeText(getActivity(), "No results for " + "'" + query + "'",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Intent myIntent = new Intent(ExploreTab.this.getActivity(),
                                    SearchActivity.class);
                            myIntent.putStringArrayListExtra("searchResultUriList",
                                    searchResultUriList);
                            ExploreTab.this.startActivity(myIntent);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(getContext(), "The read failed: " + databaseError.getCode(),
                                Toast.LENGTH_SHORT).show();
                    }

                });
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }
}