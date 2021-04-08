package com.example.dodocuisto.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SignupViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SignupViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is signup fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}