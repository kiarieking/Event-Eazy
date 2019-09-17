package com.example.myapplication.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapters.TabAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class EventActivity extends AppCompatActivity {
    private ImageView ic_heart;

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private Toolbar mToolbar;

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private NavigationView mNavigationView;
    SharedPreferences mSharedPreferences;




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
        mSharedPreferences = getApplicationContext().getSharedPreferences("myPref",0);
        String token = mSharedPreferences.getString("token", null);
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        mNavigationView = findViewById(R.id.nav_drawer);
        mDrawerLayout = findViewById(R.id.event_drawer);
        mActionBarDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.Open, R.string.Close);

        mDrawerLayout.addDrawerListener(mActionBarDrawerToggle);
        mActionBarDrawerToggle.syncState();

        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id){
                    case R.id.scheduled_event:
                        startActivity(new Intent(getApplicationContext(),
                                ScheduledEventsActivity.class));

                    case R.id.my_events:
                        Toast.makeText(EventActivity.this, "my events",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),
                                RegisterActivity.class));


                    case R.id.logout:
                        Toast.makeText(EventActivity.this, "log out",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EventActivity.this, LoginActivity.class));

                    default:
                        return true;
                }

            }
        });


        TabAdapter tabAdapter = new TabAdapter(getSupportFragmentManager(),EventActivity.this,
                mTabLayout.getTabCount());
        mViewPager.setAdapter(tabAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mActionBarDrawerToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
