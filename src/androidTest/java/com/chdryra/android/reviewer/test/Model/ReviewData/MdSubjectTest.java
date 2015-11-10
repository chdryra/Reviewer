/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdSubject;
import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdSubjectTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testGetters() {
        String title = RandomString.nextWord();
        MdSubject subject = new MdSubject(ID, title);
        assertEquals(title, subject.getSubject());
        assertEquals(ID, subject.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        String title = RandomString.nextWord();
        MdSubject subject = new MdSubject(ID, "");
        assertFalse(subject.hasData());
        subject = new MdSubject(ID, title);
        assertTrue(subject.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        String title1 = RandomString.nextWord();
        String title2 = RandomString.nextWord();
        MdReviewId id2 = RandomReviewId.nextId();

        MdSubject subject = new MdSubject(ID, title1);
        MdDataUtils.testEqualsHash(subject, new MdSubject(ID, title2), false);
        MdDataUtils.testEqualsHash(subject, new MdSubject(id2, title1), false);
        MdDataUtils.testEqualsHash(subject, new MdSubject(ID, title1), true);
    }
}
