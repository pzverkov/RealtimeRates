package com.revolut.realtimerates.domain.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CurrencyItem.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CurrencyDao currencyDao();
}