package com.revolut.realtimerates.domain.room;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CurrencyItemTest {

    private CurrencyItem currencyItem;
    private static final String TEXT = "Test";
    private static final float VALUE = 1.11111f;

    @Before
    public void setUp() {
        currencyItem = new CurrencyItem(TEXT, VALUE);
    }

    @Test
    public void setDescription() {
        assertNotEquals(currencyItem.setDescription("TEST1").getDescription(), TEXT);
    }

    @Test
    public void getDescription() {
        assertEquals(currencyItem.getDescription(), TEXT);
    }

    @Test
    public void setValue() {
        assertNotEquals(currencyItem.setValue(1.0f).getValue(), VALUE);
    }

    @Test
    public void getValue() {
        assertEquals(currencyItem.getValue(), VALUE, 0.0);
    }

    @Test
    public void compareTo() {
        assertEquals(currencyItem.compareTo(currencyItem.clone()), 0);
    }
}
