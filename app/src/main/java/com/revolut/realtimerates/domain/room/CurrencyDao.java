package com.revolut.realtimerates.domain.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CurrencyDao {
    @Query("SELECT * FROM currencies")
    List<CurrencyItem> getAll();

    @Query("SELECT * FROM currencies WHERE currency_name LIKE :name LIMIT 1")
    CurrencyItem findByCodeName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(CurrencyItem... currencyItems);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<CurrencyItem> currencyItemList);

    @Delete
    void delete(CurrencyItem currency);

    @Query("DELETE FROM currencies")
    void deleteAll();
}
