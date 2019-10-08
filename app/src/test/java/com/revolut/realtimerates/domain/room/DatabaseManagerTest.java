package com.revolut.realtimerates.domain.room;

import com.revolut.realtimerates.presentation.ui.mainscreen.MainActivity;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricTestRunner.class)
public class DatabaseManagerTest {
    private MainActivity activity;

    @Before
    public void setUp() throws Exception {
        activity = Robolectric.buildActivity(MainActivity.class)
                .create()
                .resume()
                .get();
    }

    @Test
    public void getAppDatabase() {
        DatabaseManager databaseManager = DatabaseManager.getInstance(activity);
        assertNotNull(databaseManager.getAppDatabase());
    }

    @Test
    public void getInstance() {
        assertNotNull(DatabaseManager.getInstance(activity));
    }

    @Test
    public void shouldNotBeNull() throws Exception {
        assertNotNull(activity);
    }
}
