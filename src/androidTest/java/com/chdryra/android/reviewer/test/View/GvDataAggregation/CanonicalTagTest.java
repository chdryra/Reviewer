/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.DataAggregation.Implementation.CanonicalTagMode;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvTag;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagTest extends CanonicalGvDataTest<GvTag> {
    private static final String TAG1 = RandomString.nextWord();
    private static final String TAG2 = RandomString.nextWord();
    private static final String TAG3 = RandomString.nextWord();

    //protected methods
    @Override
    protected GvTag getTestDatum() {
        return new GvTag(TAG1);
    }

    @Override
    protected CanonicalDatumMaker<GvTag> getCanonicalMaker() {
        return new CanonicalTagMode();
    }

    private void checkDifferent() {
        mData = newDataList();
        GvTag tag1 = new GvTag(TAG1);
        GvTag tag2 = new GvTag(TAG2);
        GvTag tag3 = new GvTag(TAG3);
        GvTag tag4 = new GvTag(TAG2);
        GvTag tag5 = new GvTag(TAG2);
        GvTag tag6 = new GvTag(TAG3);
        mData.add(tag1);
        mData.add(tag2);
        mData.add(tag3);
        mData.add(tag4);
        mData.add(tag5);
        mData.add(tag6);

        GvTag canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(TAG2 + " + 2", canon.getString());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferent();
    }
}
