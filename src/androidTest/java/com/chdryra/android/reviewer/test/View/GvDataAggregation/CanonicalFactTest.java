/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalFact;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalFactTest extends CanonicalGvDataTest<GvFactList.GvFact> {
    private static final String LABEL1 = RandomString.nextWord();
    private static final String LABEL2 = RandomString.nextWord();
    private static final String LABEL3 = RandomString.nextWord();
    private static final String LABEL4 = RandomString.nextWord();
    private static final String VALUE1 = RandomString.nextWord();
    private static final String VALUE2 = RandomString.nextWord();
    private static final String VALUE3 = RandomString.nextWord();
    private static final String VALUE4 = RandomString.nextWord();

    @Override
    protected GvFactList.GvFact getTestDatum() {
        return new GvFactList.GvFact(LABEL1, VALUE1);
    }

    @Override
    protected CanonicalDatumMaker<GvFactList.GvFact> getCanonicalMaker() {
        return new CanonicalFact();
    }

    @Override
    protected void additionalTests() {
        checkDifferentFacts();
        checkSameLabelDifferentValues();
        checkDifferentLabelsSameValues();
    }

    private void checkDifferentFacts() {
        mData = newDataList();
        GvFactList.GvFact fact1 = new GvFactList.GvFact(LABEL1, VALUE1);
        GvFactList.GvFact fact2 = new GvFactList.GvFact(LABEL2, VALUE2);
        GvFactList.GvFact fact3 = new GvFactList.GvFact(LABEL3, VALUE3);
        GvFactList.GvFact fact4 = new GvFactList.GvFact(LABEL4, VALUE4);
        GvFactList.GvFact fact5 = new GvFactList.GvFact(LABEL1, VALUE4);
        GvFactList.GvFact fact6 = new GvFactList.GvFact(LABEL1, VALUE2);
        GvFactList.GvFact fact7 = new GvFactList.GvFact(LABEL2, VALUE2);
        mData.add(fact1);
        mData.add(fact2);
        mData.add(fact3);
        mData.add(fact4);
        mData.add(fact5);
        mData.add(fact6);
        mData.add(fact7);
        GvFactList.GvFact canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(LABEL1 + " + 3", canon.getLabel());
        assertEquals("4 values", canon.getValue());
    }

    private void checkSameLabelDifferentValues() {
        mData = newDataList();
        GvFactList.GvFact fact1 = new GvFactList.GvFact(LABEL1, VALUE1);
        GvFactList.GvFact fact2 = new GvFactList.GvFact(LABEL1, VALUE2);
        GvFactList.GvFact fact3 = new GvFactList.GvFact(LABEL1, VALUE3);
        GvFactList.GvFact fact4 = new GvFactList.GvFact(LABEL1, VALUE4);
        GvFactList.GvFact fact5 = new GvFactList.GvFact(LABEL1, VALUE4);
        GvFactList.GvFact fact6 = new GvFactList.GvFact(LABEL1, VALUE2);
        GvFactList.GvFact fact7 = new GvFactList.GvFact(LABEL1, VALUE2);
        mData.add(fact1);
        mData.add(fact2);
        mData.add(fact3);
        mData.add(fact4);
        mData.add(fact5);
        mData.add(fact6);
        mData.add(fact7);
        GvFactList.GvFact canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(LABEL1, canon.getLabel());
        assertEquals("4 values", canon.getValue());
    }

    private void checkDifferentLabelsSameValues() {
        mData = newDataList();
        GvFactList.GvFact fact1 = new GvFactList.GvFact(LABEL2, VALUE1);
        GvFactList.GvFact fact2 = new GvFactList.GvFact(LABEL2, VALUE1);
        GvFactList.GvFact fact3 = new GvFactList.GvFact(LABEL2, VALUE1);
        GvFactList.GvFact fact4 = new GvFactList.GvFact(LABEL3, VALUE1);
        GvFactList.GvFact fact5 = new GvFactList.GvFact(LABEL3, VALUE1);
        GvFactList.GvFact fact6 = new GvFactList.GvFact(LABEL4, VALUE1);
        GvFactList.GvFact fact7 = new GvFactList.GvFact(LABEL4, VALUE1);
        mData.add(fact1);
        mData.add(fact2);
        mData.add(fact3);
        mData.add(fact4);
        mData.add(fact5);
        mData.add(fact6);
        mData.add(fact7);
        GvFactList.GvFact canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(LABEL2 + " + 2", canon.getLabel());
        assertEquals(VALUE1, canon.getValue());
    }
}
