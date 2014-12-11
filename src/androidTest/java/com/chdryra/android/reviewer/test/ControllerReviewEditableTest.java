/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ControllerReviewEditable;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.test.TestUtils.ControllerEditableTester;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewEditableTest extends TestCase {
    private ControllerEditableTester mTester;

    @SmallTest
    public void testSetSubject() {
        mTester.testSetSubject();
    }

    @SmallTest
    public void testSetRating() {
        mTester.testSetRating();
    }

    @SmallTest
    public void testSetData() {
        mTester.testSetData();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        ReviewEditable review = ReviewMocker.newReviewEditable();
        mTester = new ControllerEditableTester(new ControllerReviewEditable(review), review);
    }
}
