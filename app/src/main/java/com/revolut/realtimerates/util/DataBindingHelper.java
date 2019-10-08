package com.revolut.realtimerates.util;

import android.widget.EditText;
import com.revolut.realtimerates.presentation.ui.viewModels.CurrencyViewModel;
import org.jetbrains.annotations.NotNull;
import androidx.databinding.BindingAdapter;
import static com.revolut.realtimerates.util.Constants.CURRENCY_FORMATTER;

public class DataBindingHelper {
    @BindingAdapter({"value", "mainViewModel"})
    public static void setTotalValue(@NotNull EditText editText, float value, CurrencyViewModel mainViewModel) {
        editText.setText(CURRENCY_FORMATTER.format(value * mainViewModel.getMultiplier().get()));
    }
}
