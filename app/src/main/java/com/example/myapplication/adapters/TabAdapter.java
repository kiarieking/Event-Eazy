package com.example.myapplication.adapters;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapplication.ui.fragments.DisplayEventFragmet;
import com.example.myapplication.ui.fragments.UploadEventFragment;

public class TabAdapter extends FragmentPagerAdapter {
    Context mContext;
    public TabAdapter(FragmentManager fm,Context context) {
        super(fm);
        mContext=context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new UploadEventFragment();
            case 1:
                return new DisplayEventFragmet();

        }

        return null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Upcoming Events";
            case 1:
                return "Post your Event";
        }
        return null;
    }
}
