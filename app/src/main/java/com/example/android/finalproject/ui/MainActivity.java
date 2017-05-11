package com.example.android.finalproject.ui;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.android.finalproject.R;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        // Called to instantiate the fragment for the given page.
        @Override
        public Fragment getItem(int position) {
            ExploreTab exploreTab = new ExploreTab();
            switch (position) {
                case 0:
                    return exploreTab;
                case 1:
                    FavoritesTab favoritesTab = new FavoritesTab();
                    return favoritesTab;
                default:
                    return exploreTab;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "EXPLORE";
                case 1:
                    return "FAVORITES";
            }
            return null;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ColorDrawable colorDrawableLight = new ColorDrawable(Color.parseColor("#da3c2f"));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackground(colorDrawableLight);

        // The PagerAdapter that will provide fragments for each of the sections.
        // The FragmentPagerAdapter derivative will keep every loaded fragment in memory.
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setBackground(colorDrawableLight);
    }
}