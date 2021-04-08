package com.example.dodocuisto.ui.signup;

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

public class SignupFragment extends Fragment {

    private SignupViewModel signupViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        signupViewModel =
                new ViewModelProvider(this).get(SignupViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        final TextView textView = root.findViewById(R.id.text_home);
        signupViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}