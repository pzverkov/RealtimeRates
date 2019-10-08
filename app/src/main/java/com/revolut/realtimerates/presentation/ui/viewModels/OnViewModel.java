package com.revolut.realtimerates.presentation.ui.viewModels;

import androidx.databinding.ObservableField;
import androidx.databinding.ObservableFloat;

public interface OnViewModel {
    ObservableField<String> getBaseCurrency();
    ObservableFloat getMultiplier();
    void setMultiplier(Float multiplier);
}
