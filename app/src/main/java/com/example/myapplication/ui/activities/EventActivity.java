package com.example.myapplication.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.myapplication.R;
import com.example.myapplication.adapters.TabAdapter;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends AppCompatActivity {
    private ImageView ic_heart;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        mToolbar = findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        mTabLayout = findViewById(R.id.tab_layout);
        TabItem newEvents = findViewById(R.id.new_events);
        TabItem uploadEvents = findViewById(R.id.upload_events);
        mViewPager = findViewById(R.id.viewpager);

        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(),EventActivity.this,
                mTabLayout.getTabCount());
        mViewPager.setAdapter(tabAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }


}
