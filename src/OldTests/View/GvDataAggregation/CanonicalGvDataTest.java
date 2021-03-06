/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault
        .Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Factories.FactoryGvData;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CanonicalGvDataTest<T extends GvData> extends TestCase {
    protected GvDataListImpl<T> mData;
    protected CanonicalDatumMaker<T> mCanonical;

    protected abstract void additionalTests();

    @SmallTest
    public void testGetCanonical() {
        checkEmpty();
        checkSame();
        additionalTests();
    }

    //protected methods
    protected abstract T getTestDatum();

    protected abstract CanonicalDatumMaker<T> getCanonicalMaker();

    protected GvDataListImpl<T> newDataList() {
        return (GvDataListImpl<T>) FactoryGvData.newDataList(getTestDatum().getGvDataType(), null);
    }

    protected void checkSame() {
        T datum = getTestDatum();
        mData.add(datum);
        T canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        checkEquality(datum, canon);

        mData.add(datum);
        mData.add(datum);

        canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        checkEquality(datum, canon);
    }

    protected void checkEquality(T lhs, T rhs) {
        assertEquals(lhs, rhs);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mCanonical = getCanonicalMaker();
        //TODO make type safe
        mData = newDataList();
    }

    private void checkEmpty() {
        T canon = mCanonical.getCanonical(mData);
        assertFalse(canon.isValidForDisplay());
    }
}
