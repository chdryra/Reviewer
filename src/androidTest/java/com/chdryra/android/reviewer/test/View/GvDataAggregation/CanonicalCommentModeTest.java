/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.CanonicalCommentMode;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentModeTest extends CanonicalGvDataTest<GvComment> {
    private static final String COMMENT1 = RandomString.nextSentence();
    private static final String COMMENT2 = RandomString.nextSentence();
    private static final String COMMENT3 = RandomString.nextSentence();
    private static final String COMMENT4 = RandomString.nextSentence();

//protected methods
    @Override
    protected GvComment getTestDatum() {
        return new GvComment(COMMENT1);
    }

    @Override
    protected CanonicalDatumMaker<GvComment> getCanonicalMaker() {
        return new CanonicalCommentMode();
    }

    private void checkDifferentComments() {
        mData = newDataList();
        GvComment comment1 = new GvComment(COMMENT2);
        GvComment comment2 = new GvComment(COMMENT2);
        GvComment comment3 = new GvComment(COMMENT3);
        GvComment comment4 = new GvComment(COMMENT2);
        GvComment comment5 = new GvComment(COMMENT2);
        GvComment comment6 = new GvComment(COMMENT4);
        mData.add(comment1);
        mData.add(comment2);
        mData.add(comment3);
        mData.add(comment4);
        mData.add(comment5);
        mData.add(comment6);

        GvComment canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(COMMENT2 + " + 2", canon.getComment());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferentComments();
    }
}
