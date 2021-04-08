package com.example.dodocuisto.ui.checkmeal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.dodocuisto.R;

public class CheckMeal extends Fragment {

    private CheckMealViewModel checkMealViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        checkMealViewModel =
                new ViewModelProvider(this).get(CheckMealViewModel.class);
        View root = inflater.inflate(R.layout.fragment_checkmeal, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        checkMealViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}