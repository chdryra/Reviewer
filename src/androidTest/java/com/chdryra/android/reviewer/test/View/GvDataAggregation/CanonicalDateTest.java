/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.DataAggregation.Implementation.CanonicalDate;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDate;
import com.chdryra.android.testutils.RandomDate;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDateTest extends CanonicalGvDataTest<GvDate> {
    private static final Date DATE1 = RandomDate.nextDate();
    private static final Date DATE2 = RandomDate.nextDate();
    private static final Date DATE3 = RandomDate.nextDate();
    private static final Date DATE4 = RandomDate.nextDate();

    //protected methods
    @Override
    protected GvDate getTestDatum() {
        return new GvDate(DATE1);
    }

    @Override
    protected CanonicalDatumMaker<GvDate> getCanonicalMaker() {
        return new CanonicalDate();
    }

    private void checkDifferentDates() {
        mData = newDataList();
        GvDate date1 = new GvDate(DATE1);
        GvDate date2 = new GvDate(DATE2);
        GvDate date3 = new GvDate(DATE3);
        GvDate date4 = new GvDate(DATE4);
        mData.add(date1);
        mData.add(date2);
        mData.add(date3);
        mData.add(date4);
        mData.add(date4);
        mData.add(date4);

        Date maxDate = DATE1;
        if (DATE2.after(maxDate)) maxDate = DATE2;
        if (DATE3.after(maxDate)) maxDate = DATE3;
        if (DATE4.after(maxDate)) maxDate = DATE4;

        GvDate canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(maxDate, canon.getDate());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferentDates();
    }
}
