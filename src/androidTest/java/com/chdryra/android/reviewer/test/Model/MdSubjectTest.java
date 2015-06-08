/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdSubjectTest extends TestCase {
    private static final ReviewId ID = ReviewId.generateId();

    @SmallTest
    public void testGetters() {
        String title = RandomString.nextWord();
        MdSubject subject = new MdSubject(title, ID);
        assertEquals(title, subject.get());
        assertEquals(ID, subject.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        String title = RandomString.nextWord();
        MdSubject subject = new MdSubject("", ID);
        assertFalse(subject.hasData());
        subject = new MdSubject(title, ID);
        assertTrue(subject.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        String title1 = RandomString.nextWord();
        String title2 = RandomString.nextWord();
        ReviewId id2 = ReviewId.generateId();

        MdSubject subject = new MdSubject(title1, ID);
        MdDataUtils.testEqualsHash(subject, new MdSubject(title2, ID), false);
        MdDataUtils.testEqualsHash(subject, new MdSubject(title1, id2), false);
        MdDataUtils.testEqualsHash(subject, new MdSubject(title1, ID), true);
    }
}
