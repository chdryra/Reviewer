/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.ActivityInstrumentationTestCase2;

import com.chdryra.android.reviewer.ActivityReviewFacts;
import com.robotium.solo.Solo;

/**
 * Created by: Rizwan Choudrey
 * On: 15/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class DialogGvDataAddFactTest extends ActivityInstrumentationTestCase2<ActivityReviewFacts> {
    private Solo mSolo;

    public DialogGvDataAddFactTest() {
        super(ActivityReviewFacts.class);
    }

    @Override
    public void setUp() throws Exception {
        mSolo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        mSolo.finishOpenedActivities();
    }
}
