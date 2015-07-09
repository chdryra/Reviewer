/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataAlgorithms.CanonicalTagMode;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalTagTest extends CanonicalGvDataTest<GvTagList.GvTag> {
    private static final String TAG1 = RandomString.nextWord();
    private static final String TAG2 = RandomString.nextWord();
    private static final String TAG3 = RandomString.nextWord();

    @Override
    protected GvTagList.GvTag getTestDatum() {
        return new GvTagList.GvTag(TAG1);
    }

    @Override
    protected CanonicalDatumMaker<GvTagList.GvTag> getCanonicalMaker() {
        return new CanonicalTagMode();
    }

    @Override
    protected void additionalTests() {
        checkDifferent();
    }

    private void checkDifferent() {
        mData = newDataList();
        GvTagList.GvTag tag1 = new GvTagList.GvTag(TAG1);
        GvTagList.GvTag tag2 = new GvTagList.GvTag(TAG2);
        GvTagList.GvTag tag3 = new GvTagList.GvTag(TAG3);
        GvTagList.GvTag tag4 = new GvTagList.GvTag(TAG2);
        GvTagList.GvTag tag5 = new GvTagList.GvTag(TAG2);
        GvTagList.GvTag tag6 = new GvTagList.GvTag(TAG3);
        mData.add(tag1);
        mData.add(tag2);
        mData.add(tag3);
        mData.add(tag4);
        mData.add(tag5);
        mData.add(tag6);

        GvTagList.GvTag canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(TAG2 + " + 2", canon.get());
    }
}
