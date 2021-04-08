package com.example.dodocuisto.ui.checkmeal;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CheckMealViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CheckMealViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is checkmeal fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}