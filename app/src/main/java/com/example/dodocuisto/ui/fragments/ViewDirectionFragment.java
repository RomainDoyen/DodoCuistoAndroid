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

import com.example.dodocuisto.controller.DirectionController;
import com.example.dodocuisto.modele.Direction;


public class ViewDirectionFragment extends Fragment {
    private List<Direction> directionList;
    private DirectionController directionAdapter;

    private RecyclerView directionRecyclerView;
    private TextView emptyView;

    public ViewDirectionFragment() {
        // Required empty public constructor
    }

    public static ViewDirectionFragment newInstance(List<Direction> directions) {
        ViewDirectionFragment fragment = new ViewDirectionFragment();
        if (directions == null)
            directions = new ArrayList<>();
        Bundle args = new Bundle();
        args.putParcelableArrayList("directions", (ArrayList<Direction>) directions);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_directions, container, false);

        Bundle args = getArguments();
        if (args != null)
            directionList = args.getParcelableArrayList("directions");

        directionRecyclerView = view.findViewById(R.id.recyclerView);
        emptyView = view.findViewById(R.id.empty_view);

        directionAdapter = new DirectionController(getActivity(), directionList, false);
        toggleEmptyView();

        directionRecyclerView.setHasFixedSize(true);
        directionRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        directionRecyclerView.setAdapter(directionAdapter);

        return view;
    }

    private void toggleEmptyView() {
        if (directionList.size() == 0) {
            emptyView.setVisibility(View.VISIBLE);
            directionRecyclerView.setVisibility(View.GONE);
        } else {
            emptyView.setVisibility(View.GONE);
            directionRecyclerView.setVisibility(View.VISIBLE);
        }
    }
}
