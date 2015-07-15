/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 July, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalDatumMaker;
import com.chdryra.android.reviewer.View.GvDataAggregation.CanonicalSubjectMode;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 09/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class CanonicalSubjectTest extends CanonicalGvDataTest<GvSubjectList.GvSubject> {
    private static final String SUBJECT1 = RandomString.nextWord();
    private static final String SUBJECT2 = RandomString.nextWord();
    private static final String SUBJECT3 = RandomString.nextWord();

    @Override
    protected GvSubjectList.GvSubject getTestDatum() {
        return new GvSubjectList.GvSubject(SUBJECT1);
    }

    @Override
    protected CanonicalDatumMaker<GvSubjectList.GvSubject> getCanonicalMaker() {
        return new CanonicalSubjectMode();
    }

    @Override
    protected void additionalTests() {
        checkDifferent();
    }

    private void checkDifferent() {
        mData = newDataList();
        GvSubjectList.GvSubject subject1 = new GvSubjectList.GvSubject(SUBJECT1);
        GvSubjectList.GvSubject subject2 = new GvSubjectList.GvSubject(SUBJECT2);
        GvSubjectList.GvSubject subject3 = new GvSubjectList.GvSubject(SUBJECT3);
        GvSubjectList.GvSubject subject4 = new GvSubjectList.GvSubject(SUBJECT2);
        GvSubjectList.GvSubject subject5 = new GvSubjectList.GvSubject(SUBJECT2);
        GvSubjectList.GvSubject subject6 = new GvSubjectList.GvSubject(SUBJECT3);
        mData.add(subject1);
        mData.add(subject2);
        mData.add(subject3);
        mData.add(subject4);
        mData.add(subject5);
        mData.add(subject6);

        GvSubjectList.GvSubject canon = mCanonical.getCanonical(mData);
        assertTrue(canon.isValidForDisplay());
        assertEquals(SUBJECT2 + " + 2", canon.get());
    }
}
