package com.revolut.realtimerates.domain.room;
import android.content.Context;

import com.revolut.realtimerates.util.Constants;

import androidx.room.Room;

public class DatabaseManager {
    private static final boolean USE_ENCRYPTED_DB = false;
    private static DatabaseManager instance;
    private AppDatabase appDatabase;

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager();

            if (USE_ENCRYPTED_DB) {
                //TODO: In case we want to support encrypted DB
                //instance.appDatabase = Room.databaseBuilder(this, AppDatabase.class, Constants.CURRENCY_RATES)
                //.openHelperFactory(SafeHelperFactory.fromUser(Editable.Factory.getInstance().newEditable(Constants.PHRASE)))
                //.build();

                //TODO: For checking encrypted state
                //instance.appDatabase.getDatabaseState()
            } else {
                instance.appDatabase = Room.databaseBuilder(context, AppDatabase.class, Constants.CURRENCY_RATES).build();
            }
        }
        return instance;
    }

    public AppDatabase getAppDatabase() {
        return appDatabase;
    }
}
