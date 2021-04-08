package com.example.dodocuisto.ui.signin;

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

public class SigninFragment extends Fragment {

    private SigninViewModel signinViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signinViewModel =
                new ViewModelProvider(this).get(SigninViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signin, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        signinViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}