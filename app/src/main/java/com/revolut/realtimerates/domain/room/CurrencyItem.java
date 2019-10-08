package com.revolut.realtimerates.domain.room;

import java.io.Serializable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "currencies")
public class CurrencyItem implements Comparable<CurrencyItem>, Cloneable, Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "currency_name")
    public String description;

    @ColumnInfo(name = "currency_rate")
    public float value;

    public CurrencyItem(@NonNull String description, float value) {
        this.description = description;
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public CurrencyItem setDescription(@NonNull String description) {
        this.description = description;
        return this;
    }

    public float getValue() {
        return value;
    }

    public CurrencyItem setValue(float value) {
        this.value = value;
        return this;
    }

    @Override
    public int compareTo(@NonNull CurrencyItem item) {
        return (getDescription() == null || item.getDescription() == null) ? 0 : getDescription().compareTo(item.getDescription());
    }

    @NonNull
    @Override
    public CurrencyItem clone() {
        CurrencyItem clone;
        try {
            clone = (CurrencyItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        return clone;
    }
}
