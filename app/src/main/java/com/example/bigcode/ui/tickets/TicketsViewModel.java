package com.example.bigcode.ui.tickets;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class TicketsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public TicketsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Biletlerim");
    }

    public LiveData<String> getText() {
        return mText;
    }
}