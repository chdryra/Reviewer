/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.DataAggregation.Implementation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.DataAggregation.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverageTest extends CanonicalGvDataTest<GvCriterion> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();
    private static final float RATING1 = 1f;
    private static final float RATING2 = 2f;
    private static final float RATING3 = 3f;

//protected methods
    @Override
    protected GvCriterion getTestDatum() {
        return new GvCriterion(SUBJECT1, RATING1);
    }

    @Override
    protected CanonicalDatumMaker<GvCriterion> getCanonicalMaker() {
        return new CanonicalCriterionAverage();
    }

    private void checkSameSubjectDifferentRatings() {
        mData = newDataList();
        GvCriterion review1 = new GvCriterion(SUBJECT1,
                RATING1);
        GvCriterion review2 = new GvCriterion(SUBJECT1,
                RATING2);
        GvCriterion review3 = new GvCriterion(SUBJECT1,
                RATING3);
        GvCriterion review4 = new GvCriterion(SUBJECT1,
                RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);

        float avg = 0f;
        for (GvCriterion child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvCriterion canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT1, canon.getSubject());
        assertEquals(avg, canon.getRating());
    }

    private void checkDifferent() {
        mData = newDataList();
        GvCriterion review1 = new GvCriterion(SUBJECT2,
                RATING1);
        GvCriterion review2 = new GvCriterion(SUBJECT2,
                RATING2);
        GvCriterion review3 = new GvCriterion(SUBJECT3,
                RATING3);
        GvCriterion review4 = new GvCriterion(SUBJECT2,
                RATING3);
        GvCriterion review5 = new GvCriterion(SUBJECT2,
                RATING3);
        GvCriterion review6 = new GvCriterion(SUBJECT3, RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);
        mData.add(review5);
        mData.add(review6);

        float avg = 0f;
        for (GvCriterion child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvCriterion canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT2 + " + 1", canon.getSubject());
        assertEquals(avg, canon.getRating());
    }

    //Overridden
    @Override
    protected void additionalTests() {
        checkDifferent();
        checkSameSubjectDifferentRatings();
    }
}
