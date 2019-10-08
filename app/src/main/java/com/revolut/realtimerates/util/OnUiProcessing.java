package com.revolut.realtimerates.util;

import android.view.MotionEvent;
import android.view.View;
import com.revolut.realtimerates.domain.room.CurrencyItem;

public interface OnUiProcessing extends View.OnClickListener {
    @Override
    void onClick(View view);
    void onCellClick(View view, CurrencyItem item);
    boolean onTouchListener(View view, MotionEvent motionEvent);
    void onTextChanged(CharSequence s, int start, int before, int count, CurrencyItem currencyItem);
}