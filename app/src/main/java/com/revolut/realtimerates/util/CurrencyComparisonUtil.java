package com.revolut.realtimerates.util;

import android.os.Bundle;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import java.util.List;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

public class CurrencyComparisonUtil extends DiffUtil.Callback {

    static final String KEY_CODE = "CODE";
    static final String KEY_VALUE = "VALUE";

    private List<CurrencyItem> newValues;
    private List<CurrencyItem> oldValues;

    public CurrencyComparisonUtil(List<CurrencyItem> newValues, List<CurrencyItem> oldValues) {
        this.newValues = newValues;
        this.oldValues = oldValues;
    }

    @Override
    public final int getOldListSize() {
        return oldValues.size();
    }

    @Override
    public final int getNewListSize() {
        return newValues.size();
    }

    @Override
    public final boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return true;
    }

    @Override
    public final boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        CurrencyItem oldItem = oldValues.get(oldItemPosition);
        CurrencyItem newItem = newValues.get(newItemPosition);
        return newItem.getDescription().equals(oldItem.getDescription())
                && newItem.getDescription().equals(oldItem.getDescription())
                && sameValues(newItem.getValue(), oldItem.getValue());
    }

    private boolean sameValues(float newValue, float oldValue) {
        String newVal = String.valueOf(newValue);
        String oldVal = String.valueOf(oldValue);
        int newDecimalIndex = newVal.indexOf(".");
        int oldDecimalIndex = oldVal.indexOf(".");

        if (newDecimalIndex != oldDecimalIndex)
            return false;

        if (newDecimalIndex == -1 || newVal.length() < newDecimalIndex + 3 || newVal.length() != oldVal.length())
            return newVal.equals(oldVal);

        return newVal.substring(0, newDecimalIndex + 3).equals(oldVal.substring(0, oldDecimalIndex + 3));
    }

    @Nullable
    @Override
    public final Object getChangePayload(int oldItemPosition, int newItemPosition) {
        CurrencyItem oldItem = oldValues.get(oldItemPosition);
        CurrencyItem newItem = newValues.get(newItemPosition);

        Bundle diffBundle = new Bundle();

        if (!newItem.getDescription().equals(oldItem.getDescription()))
            diffBundle.putString(KEY_CODE, newItem.getDescription());
        if (newItem.getValue() != (oldItem.getValue()))
            diffBundle.putFloat(KEY_VALUE, newItem.getValue());

        if (diffBundle.isEmpty())
            return null;
        return diffBundle;
    }
}