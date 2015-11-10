/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Models.ReviewsModel.ReviewsData.MdFactList;
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
public class MdFactListTest extends TestCase {
    private static final MdReviewId ID = RandomReviewId.nextId();

    @SmallTest
    public void testMdFactHasData() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        MdFactList.MdFact fact = new MdFactList.MdFact(ID, null, null);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, "", null);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, null, "");
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, "", "");
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, label, "");
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, "", value);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(ID, label, value);
        assertTrue(fact.hasData());
    }

    @SmallTest
    public void testMdFactGetters() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        MdFactList.MdFact fact = new MdFactList.MdFact(ID, label, value);
        assertEquals(label, fact.getLabel());
        assertEquals(value, fact.getValue());
        assertEquals(ID, fact.getReviewId());
    }

    @SmallTest
    public void testIsUrl() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        MdFactList.MdFact fact = new MdFactList.MdFact(ID, label, value);
        assertFalse(fact.isUrl());
    }

    @SmallTest
    public void testMdFactEqualsHash() {
        String label1 = RandomString.nextWord();
        String value1 = RandomString.nextWord();
        String label2 = RandomString.nextWord();
        String value2 = RandomString.nextWord();
        MdReviewId id2 = RandomReviewId.nextId();

        MdFactList.MdFact fact1 = new MdFactList.MdFact(ID, label1, value1);

        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(ID, label1, value2), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(ID, label2, value1), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(id2, label1, value1), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(ID, label1, value1), true);
    }
}
