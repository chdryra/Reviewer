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
import com.chdryra.android.reviewer.View.GvDataModel.GvChildList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalChildReviewTest extends CanonicalGvDataTest<GvChildList.GvChildReview> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();
    private static final float  RATING1  = 1f;
    private static final float  RATING2  = 2f;
    private static final float  RATING3  = 3f;

    @Override
    protected GvChildList.GvChildReview getTestDatum() {
        return new GvChildList.GvChildReview(SUBJECT1, RATING1);
    }

    @Override
    protected CanonicalDatumMaker<GvChildList.GvChildReview> getCanonicalMaker() {
        return new CanonicalChildReview();
    }

    @Override
    protected void additionalTests() {
        checkDifferent();
        checkSameSubjectDifferentRatings();
    }

    private void checkSameSubjectDifferentRatings() {
        mData = newDataList();
        GvChildList.GvChildReview review1 = new GvChildList.GvChildReview(SUBJECT1, RATING1);
        GvChildList.GvChildReview review2 = new GvChildList.GvChildReview(SUBJECT1, RATING2);
        GvChildList.GvChildReview review3 = new GvChildList.GvChildReview(SUBJECT1, RATING3);
        GvChildList.GvChildReview review4 = new GvChildList.GvChildReview(SUBJECT1, RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);

        float avg = 0f;
        for (GvChildList.GvChildReview child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvChildList.GvChildReview canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT1, canon.getSubject());
        assertEquals(avg, canon.getRating());
    }

    private void checkDifferent() {
        mData = newDataList();
        GvChildList.GvChildReview review1 = new GvChildList.GvChildReview(SUBJECT2, RATING1);
        GvChildList.GvChildReview review2 = new GvChildList.GvChildReview(SUBJECT2, RATING2);
        GvChildList.GvChildReview review3 = new GvChildList.GvChildReview(SUBJECT3, RATING3);
        GvChildList.GvChildReview review4 = new GvChildList.GvChildReview(SUBJECT2, RATING3);
        GvChildList.GvChildReview review5 = new GvChildList.GvChildReview(SUBJECT2, RATING3);
        GvChildList.GvChildReview review6 = new GvChildList.GvChildReview(SUBJECT3, RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);
        mData.add(review5);
        mData.add(review6);

        float avg = 0f;
        for (GvChildList.GvChildReview child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvChildList.GvChildReview canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT2 + " + 1", canon.getSubject());
        assertEquals(avg, canon.getRating());
    }
}
