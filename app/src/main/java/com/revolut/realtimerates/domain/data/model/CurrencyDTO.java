package com.revolut.realtimerates.domain.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.Map;
import androidx.annotation.NonNull;

public class CurrencyDTO {
    @SerializedName("base")
    private String baseCurrency;
    @SerializedName("date")
    private Date date;
    @SerializedName("rates")
    private Map<String, Float> rates;

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public Date getDate() {
        return date;
    }

    public Map<String, Float> getRates() {
        return rates;
    }

    @NonNull
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Currency Data From API {baseCurrency='");
        stringBuilder.append(baseCurrency);
        stringBuilder.append('\'');
        stringBuilder.append(", date=");
        stringBuilder.append(date);
        stringBuilder.append(", rates=");
        stringBuilder.append(rates);
        stringBuilder.append('}');
        String result = stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }
}
