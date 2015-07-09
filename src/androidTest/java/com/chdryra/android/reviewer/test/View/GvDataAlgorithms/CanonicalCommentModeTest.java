/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataAlgorithms.CanonicalCommentMode;
import com.chdryra.android.reviewer.View.GvDataAlgorithms.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCommentModeTest extends CanonicalGvDataTest<GvCommentList.GvComment> {
    private static final String COMMENT1 = RandomString.nextSentence();
    private static final String COMMENT2 = RandomString.nextSentence();
    private static final String COMMENT3 = RandomString.nextSentence();
    private static final String COMMENT4 = RandomString.nextSentence();

    @Override
    protected GvCommentList.GvComment getTestDatum() {
        return new GvCommentList.GvComment(COMMENT1);
    }

    @Override
    protected CanonicalDatumMaker<GvCommentList.GvComment> getCanonicalMaker() {
        return new CanonicalCommentMode();
    }

    @Override
    protected void additionalTests() {
        checkDifferentComments();
    }

    private void checkDifferentComments() {
        mData = newDataList();
        GvCommentList.GvComment comment1 = new GvCommentList.GvComment(COMMENT2);
        GvCommentList.GvComment comment2 = new GvCommentList.GvComment(COMMENT2);
        GvCommentList.GvComment comment3 = new GvCommentList.GvComment(COMMENT3);
        GvCommentList.GvComment comment4 = new GvCommentList.GvComment(COMMENT2);
        GvCommentList.GvComment comment5 = new GvCommentList.GvComment(COMMENT2);
        GvCommentList.GvComment comment6 = new GvCommentList.GvComment(COMMENT4);
        mData.add(comment1);
        mData.add(comment2);
        mData.add(comment3);
        mData.add(comment4);
        mData.add(comment5);
        mData.add(comment6);

        GvCommentList.GvComment canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(COMMENT2 + " + 2", canon.getComment());
    }
}
