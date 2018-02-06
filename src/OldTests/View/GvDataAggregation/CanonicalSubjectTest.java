/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataAggregation;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Implementation.CanonicalSubjectMode;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin.DataAggregatorsDefault.FactoryDataAggregatorDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvSubject;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectTest extends CanonicalGvDataTest<GvSubject> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();

//protected methods
    @Override
    protected GvSubject getTestDatum() {
        return new GvSubject(SUBJECT1);
    }

    @Override
    protected CanonicalDatumMaker<GvSubject> getCanonicalMaker() {
        return new CanonicalSubjectMode();
    }

    private void checkDifferent() {
        mData = newDataList();
        GvSubject subject1 = new GvSubject(SUBJECT1);
        GvSubject subject2 = new GvSubject(SUBJECT2);
        GvSubject subject3 = new GvSubject(SUBJECT3);
        GvSubject subject4 = new GvSubject(SUBJECT2);
        GvSubject subject5 = new GvSubject(SUBJECT2);
        GvSubject subject6 = new GvSubject(SUBJECT3);
        mData.add(subject1);
        mData.add(subject2);
        mData.add(subject3);
        mData.add(subject4);
        mData.add(subject5);
        mData.add(subject6);

        GvSubject canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT2 + " + 2", canon.getString());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferent();
    }
}
