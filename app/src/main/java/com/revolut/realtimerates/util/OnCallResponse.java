package com.revolut.realtimerates.util;

import com.revolut.realtimerates.domain.data.model.CurrencyDTO;

public interface OnCallResponse {
    void setConverterData(CurrencyDTO data);
}