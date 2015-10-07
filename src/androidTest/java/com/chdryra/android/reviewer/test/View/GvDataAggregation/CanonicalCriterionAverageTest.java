/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalCriterionAverage;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataModel.GvCriterionList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalCriterionAverageTest extends CanonicalGvDataTest<GvCriterionList
        .GvCriterion> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();
    private static final float RATING1 = 1f;
    private static final float RATING2 = 2f;
    private static final float RATING3 = 3f;

    private void checkSameSubjectDifferentRatings() {
        mData = newDataList();
        GvCriterionList.GvCriterion review1 = new GvCriterionList.GvCriterion(SUBJECT1,
                RATING1);
        GvCriterionList.GvCriterion review2 = new GvCriterionList.GvCriterion(SUBJECT1,
                RATING2);
        GvCriterionList.GvCriterion review3 = new GvCriterionList.GvCriterion(SUBJECT1,
                RATING3);
        GvCriterionList.GvCriterion review4 = new GvCriterionList.GvCriterion(SUBJECT1,
                RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);

        float avg = 0f;
        for (GvCriterionList.GvCriterion child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvCriterionList.GvCriterion canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT1, canon.getSubject());
        assertEquals(avg, canon.getRating());
    }

    private void checkDifferent() {
        mData = newDataList();
        GvCriterionList.GvCriterion review1 = new GvCriterionList.GvCriterion(SUBJECT2,
                RATING1);
        GvCriterionList.GvCriterion review2 = new GvCriterionList.GvCriterion(SUBJECT2,
                RATING2);
        GvCriterionList.GvCriterion review3 = new GvCriterionList.GvCriterion(SUBJECT3,
                RATING3);
        GvCriterionList.GvCriterion review4 = new GvCriterionList.GvCriterion(SUBJECT2,
                RATING3);
        GvCriterionList.GvCriterion review5 = new GvCriterionList.GvCriterion(SUBJECT2,
                RATING3);
        GvCriterionList.GvCriterion review6 = new GvCriterionList.GvCriterion(SUBJECT3, RATING3);
        mData.add(review1);
        mData.add(review2);
        mData.add(review3);
        mData.add(review4);
        mData.add(review5);
        mData.add(review6);

        float avg = 0f;
        for (GvCriterionList.GvCriterion child : mData) {
            avg += child.getRating() / (float) mData.size();
        }

        GvCriterionList.GvCriterion canon = mCanonical.getCanonical(mData);
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

    @Override
    protected GvCriterionList.GvCriterion getTestDatum() {
        return new GvCriterionList.GvCriterion(SUBJECT1, RATING1);
    }

    @Override
    protected CanonicalDatumMaker<GvCriterionList.GvCriterion> getCanonicalMaker() {
        return new CanonicalCriterionAverage();
    }
}
