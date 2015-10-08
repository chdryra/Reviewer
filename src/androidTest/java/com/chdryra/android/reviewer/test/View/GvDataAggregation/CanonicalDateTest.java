/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDate;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.GvDateList;
import com.chdryra.android.testutils.RandomDate;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalDateTest extends CanonicalGvDataTest<GvDateList.GvDate> {
    private static final Date DATE1 = RandomDate.nextDate();
    private static final Date DATE2 = RandomDate.nextDate();
    private static final Date DATE3 = RandomDate.nextDate();
    private static final Date DATE4 = RandomDate.nextDate();

    //protected methods
    @Override
    protected GvDateList.GvDate getTestDatum() {
        return new GvDateList.GvDate(DATE1);
    }

    @Override
    protected CanonicalDatumMaker<GvDateList.GvDate> getCanonicalMaker() {
        return new CanonicalDate();
    }

    private void checkDifferentDates() {
        mData = newDataList();
        GvDateList.GvDate date1 = new GvDateList.GvDate(DATE1);
        GvDateList.GvDate date2 = new GvDateList.GvDate(DATE2);
        GvDateList.GvDate date3 = new GvDateList.GvDate(DATE3);
        GvDateList.GvDate date4 = new GvDateList.GvDate(DATE4);
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

        GvDateList.GvDate canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(maxDate, canon.getDate());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferentDates();
    }
}
