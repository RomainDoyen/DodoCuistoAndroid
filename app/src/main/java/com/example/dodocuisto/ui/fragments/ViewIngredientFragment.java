package com.example.dodocuisto.ui.fragments;

import com.example.dodocuisto.R;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

import com.example.dodocuisto.controller.IngredientController;
import com.example.dodocuisto.modele.Ingredient;

public class ViewIngredientFragment extends Fragment {
    private List<Ingredient> ingredientList;
    private IngredientController ingredientAdapter;

    private RecyclerView ingredientRecyclerView;
    private TextView emptyView;

    public ViewIngredientFragment() {
        // Required empty public constructor
    }

    public static ViewIngredientFragment newInstance(List<Ingredient> ingredients) {
        ViewIngredientFragment fragment = new ViewIngredientFragment();
        if (ingredients == null)
            ingredients = new ArrayList<>();
        Bundle args = new Bundle();
        args.putParcelableArrayList("ingredients", (ArrayList<Ingredient>) ingredients);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_ingredients, container, false);

        Bundle args = getArguments();
        if (args != null)
            ingredientList = args.getParcelableArrayList("ingredients");

        ingredientRecyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);

        ingredientAdapter = new IngredientController(getActivity(), ingredientList, false);
        toggleEmptyView();

        ingredientRecyclerView.setHasFixedSize(true);
        ingredientRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ingredientRecyclerView.setAdapter(ingredientAdapter);

        return view;
    }

    private void toggleEmptyView() {
        if (ingredientList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            ingredientRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            ingredientRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
