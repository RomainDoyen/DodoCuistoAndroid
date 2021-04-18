package com.example.dodocuisto.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.LayoutRes;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dodocuisto.modele.Recette;

import java.util.ArrayList;
import java.util.List;

import com.example.dodocuisto.R;
import com.example.dodocuisto.controller.DatabaseController;
import com.example.dodocuisto.controller.RecetteController;


public abstract class CategorieFragment extends Fragment {
    private CategorizedFragmentListener categorizedFragmentListener;
    protected RecyclerView recipeRecyclerView;
    private TextView emptyView;
    protected RecetteController recipeAdapter;
    protected DatabaseController databaseAdapter;
    protected String currentCategory;
    protected List<Recette> recipes;
    private Recette recipe;
    private Pair<View, String>[] pairs;
    private Object Pair;


    public CategorieFragment() {
        // Required empty public constructor
        recipes = new ArrayList<>();
        databaseAdapter = DatabaseController.getInstance(getActivity());
    }

    public static Fragment newInstance(String category) {
        Fragment fragment;
        switch (category) {
            case "Dessert":
                fragment = new DessertFragment();
                break;
            default:
                fragment = new PlatsFragment();
                break;
        }

        Bundle args = new Bundle();
        args.putString("category", category);
        fragment.setArguments(args);

        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(getFragmentLayout(), container, false);

        Bundle args = getArguments();
        currentCategory = args.getString("category");

        recipeRecyclerView = rootView.findViewById(R.id.recyclerView);
        emptyView = rootView.findViewById(R.id.empty_view);
        recipeRecyclerView.setHasFixedSize(true);
        recipeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        refresh();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            categorizedFragmentListener = (CategorizedFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement CategorizedFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        categorizedFragmentListener = null;
    }

    public void refresh() {
        recipes = databaseAdapter.getAllRecipesByCategory(currentCategory);
        toggleEmptyView();
        recipeAdapter = new RecetteController(getActivity(), recipes);
        recipeAdapter.setRecipeListener(new RecetteController.RecipeListener() {
            @Override
            public void onShowRecipe(Recette recipe, Pair<View, String>[] pairs) {
                categorizedFragmentListener.onShowRecipe(recipe, pairs);
            }
            @Override
            public void onEditRecipe(Recette recipe) {
                categorizedFragmentListener.onEditRecipe(recipe);
            }
            @Override
            public void onDeleteRecipe(long recipeId) {
                categorizedFragmentListener.onDeleteRecipe(recipeId);
                refresh();
            }
        });
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    private void toggleEmptyView() {
        if (recipes.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            recipeRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            recipeRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    protected abstract @LayoutRes
    int getFragmentLayout();

    public interface CategorizedFragmentListener {
        void onShowRecipe(Recette recipe, Pair<View, String>[] pairs);

        void onEditRecipe(Recette recipe);

        void onDeleteRecipe(long recipeId);
    }
}
