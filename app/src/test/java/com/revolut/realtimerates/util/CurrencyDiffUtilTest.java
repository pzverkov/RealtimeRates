package com.revolut.realtimerates.util;

import org.junit.Test;
import java.util.ArrayList;
import java.util.List;
import com.revolut.realtimerates.domain.room.CurrencyItem;
import static org.junit.Assert.*;

public class CurrencyDiffUtilTest {

    @Test
    public void areContentsTheSame() {
        List<CurrencyItem> newValues = new ArrayList<>();
        newValues.add(new CurrencyItem("Test",1.11111f));
        newValues.add(new CurrencyItem("Test",1.0f));
        newValues.add(new CurrencyItem("Test",1.1f));
        newValues.add(new CurrencyItem("Test",1.234f));
        newValues.add(new CurrencyItem("Test",1.11111f));
        newValues.add(new CurrencyItem("Test",1.5432f));

        List<CurrencyItem> oldValues = new ArrayList<>();
        oldValues.add(new CurrencyItem("Test",1.11211f));
        oldValues.add(new CurrencyItem("Test",1.0f));
        oldValues.add(new CurrencyItem("Test",1f));
        oldValues.add(new CurrencyItem("Test",5.11111f));
        oldValues.add(new CurrencyItem("Test",1.1f));
        oldValues.add(new CurrencyItem("Test",1.134f));

        CurrencyComparisonUtil diffUtil = new CurrencyComparisonUtil(newValues, oldValues);

        assertTrue(diffUtil.areContentsTheSame(0,0));
        assertTrue(diffUtil.areContentsTheSame(1,1));
        assertFalse(diffUtil.areContentsTheSame(2,2));
        assertFalse(diffUtil.areContentsTheSame(3,3));
        assertFalse(diffUtil.areContentsTheSame(3,3));
        assertFalse(diffUtil.areContentsTheSame(3,3));
    }
}