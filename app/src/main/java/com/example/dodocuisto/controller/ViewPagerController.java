package com.example.dodocuisto.controller;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.dodocuisto.modele.Recette;
import com.example.dodocuisto.ui.fragments.ViewIngredientFragment;
import com.example.dodocuisto.ui.fragments.ViewDirectionFragment;

public class ViewPagerController extends FragmentStatePagerAdapter {
    private static final int TAB_COUNT = 2;
    private final Recette recipe;
    private String[] tabTitles = {"Ingredients", "Directions"};

    public ViewPagerController(FragmentManager fm, Recette recipe) {
        super(fm);
        this.recipe = recipe;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment frag = null;
        switch (position) {
            case 0:
                frag = ViewIngredientFragment.newInstance(recipe.getIngredients());
                break;
            case 1:
                frag = ViewDirectionFragment.newInstance(recipe.getDirections());
                break;
        }
        return frag;
    }

    @Override
    public int getCount() {
        return TAB_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
