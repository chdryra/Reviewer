/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReviewEditable;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.MdCommentList;
import com.chdryra.android.reviewer.MdToGvConverter;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.test.TestUtils.RDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RandomStringGenerator;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewEditableTest extends AndroidTestCase {
    private static final int NUM = 100;
    private ControllerReviewEditable mController;
    private ReviewEditable           mReview;
    private RDataMocker              mRDataMocker;

    public void testSetSubject() {
        String subject = RandomStringGenerator.nextSentence();
        mController.setSubject(subject);
        assertEquals(subject, mController.getSubject());
        assertEquals(mReview.getSubject().get(), mController.getSubject());
    }

    public void testSetRating() {
        Random rand = new Random();
        float rating = rand.nextFloat() * 5;
        mController.setRating(rating);
        assertEquals(rating, mController.getRating());
        assertEquals(mReview.getRating().get(), mController.getRating());
    }

    public void testSetComments() {
        GvDataList.GvType dataType = GvDataList.GvType.COMMENTS;
        GvCommentList gvComments = (GvCommentList) mController.getData(dataType);
        MdCommentList rdComments = mReview.getComments();
        assertNotNull(gvComments);
        assertNotNull(rdComments);
        assertEquals(0, gvComments.size());
        assertEquals(0, rdComments.size());

        MdCommentList rdData = mRDataMocker.newCommentList(NUM);
        assertNotNull(rdData);
        assertTrue(rdData.size() > 0);
        GvCommentList comments = MdToGvConverter.convert(rdData);
        assertNotNull(comments);
        assertEquals(rdData.size(), comments.size());

        mController.setData(comments);

        gvComments = (GvCommentList) mController.getData(dataType);
        rdComments = mReview.getComments();
        assertNotNull(gvComments);
        assertNotNull(rdComments);
        assertEquals(rdData.size(), gvComments.size());
        assertEquals(rdData.size(), rdComments.size());
        for (int i = 0; i < rdData.size(); ++i) {
            assertEquals(rdData.getItem(i).getComment(), rdComments.getItem(i).getComment());
            assertEquals(rdData.getItem(i).getComment(), gvComments.getItem(i).getComment());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mRDataMocker = new RDataMocker(mReview);
        mReview = ReviewMocker.newReviewEditable();
        mController = new ControllerReviewEditable(mReview);
    }
}
