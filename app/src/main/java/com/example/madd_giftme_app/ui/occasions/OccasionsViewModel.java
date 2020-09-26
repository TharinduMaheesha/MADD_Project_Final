package com.example.madd_giftme_app.ui.occasions;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OccasionsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public OccasionsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is occasions fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}