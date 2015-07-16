/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalChildReview;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalChildReviewTest extends CanonicalGvDataTest<GvChildReviewList.GvChildReview> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();
    private static final float  RATING1  = 1f;
    private static final float  RATING2  = 2f;
    private static final float  RATING3  = 3f;

    @Override
    protected GvChildReviewList.GvChildReview getTestDatum() {
        return new GvChildReviewList.GvChildReview(SUBJECT1, RATING1);
    }

    @Override
    protected CanonicalDatumMaker<GvChildReviewList.GvChildReview> getCanonicalMaker() {
        return new CanonicalChildReview();
    }

    @Override
    protected void additionalTests() {
        checkDifferent();
        checkSameSubjectDifferentRatings();
    }

    private void checkSameSubjectDifferentRatings() {
        mData = newDataList();
        GvChildReviewList.GvChildReview review1 = new GvChildReviewList.GvChildReview(SUBJECT1,
                RATING1);
        GvChildReviewList.GvChildReview review2 = new GvChildReviewList.GvChildReview(SUBJECT1,
                RATING2);
        GvChildReviewList.GvChildReview review3 = new GvChildReviewList.GvChildReview(SUBJECT1,
                RATING3);
        GvChildReviewList.GvChildReview review4 = new GvChildReviewList.GvChildReview(SUBJECT1,
                RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);

        float avg = 0f;
        for (GvChildReviewList.GvChildReview child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvChildReviewList.GvChildReview canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT1, canon.getSubject());
        assertEquals(avg, canon.getRating());
    }

    private void checkDifferent() {
        mData = newDataList();
        GvChildReviewList.GvChildReview review1 = new GvChildReviewList.GvChildReview(SUBJECT2,
                RATING1);
        GvChildReviewList.GvChildReview review2 = new GvChildReviewList.GvChildReview(SUBJECT2,
                RATING2);
        GvChildReviewList.GvChildReview review3 = new GvChildReviewList.GvChildReview(SUBJECT3,
                RATING3);
        GvChildReviewList.GvChildReview review4 = new GvChildReviewList.GvChildReview(SUBJECT2,
                RATING3);
        GvChildReviewList.GvChildReview review5 = new GvChildReviewList.GvChildReview(SUBJECT2,
                RATING3);
        GvChildReviewList.GvChildReview review6 = new GvChildReviewList.GvChildReview(SUBJECT3, RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);
        mData.add(review5);
        mData.add(review6);

        float avg = 0f;
        for (GvChildReviewList.GvChildReview child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvChildReviewList.GvChildReview canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT2 + " + 1", canon.getSubject());
        assertEquals(avg, canon.getRating());
    }
}
