package com.example.dodocuisto.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dodocuisto.ui.fragments.*;

public class MainPagerController extends FragmentStatePagerAdapter {
    private static final int TAB_COUNT = 5;
    private String[] tabTitles = {"Plats Créoles", "Dessert"};

    public MainPagerController(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        Fragment frag = null;
        switch (position) {
            case 0:
                frag = PlatsFragment.newInstance(tabTitles[position]);
                break;
            case 1:
                frag = DessertFragment.newInstance(tabTitles[position]);
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
