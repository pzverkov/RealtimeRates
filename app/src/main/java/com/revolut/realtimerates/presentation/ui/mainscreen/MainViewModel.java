package com.revolut.realtimerates.presentation.ui.mainscreen;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {
    private MutableLiveData<Boolean> connected = new MutableLiveData<>();
    public MutableLiveData<Boolean> getConnected() {
        return connected;
    }
    public void setConnected(boolean connected) {
        this.connected.setValue(connected);
    }
}
