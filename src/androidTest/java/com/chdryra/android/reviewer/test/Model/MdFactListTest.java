/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.MdDataUtils;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdFactListTest extends TestCase {
    private static final ReviewId ID = ReviewId.generateId();

    @SmallTest
    public void testMdFactHasData() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();

        MdFactList.MdFact fact = new MdFactList.MdFact(null, null, ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact("", null, ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(null, "", ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact("", "", ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(label, "", ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact("", value, ID);
        assertFalse(fact.hasData());
        fact = new MdFactList.MdFact(label, value, ID);
        assertTrue(fact.hasData());
    }

    @SmallTest
    public void testMdFactGetters() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        MdFactList.MdFact fact = new MdFactList.MdFact(label, value, ID);
        assertEquals(label, fact.getLabel());
        assertEquals(value, fact.getValue());
        assertEquals(ID, fact.getReviewId());
    }

    @SmallTest
    public void testIsUrl() {
        String label = RandomString.nextWord();
        String value = RandomString.nextWord();
        MdFactList.MdFact fact = new MdFactList.MdFact(label, value, ID);
        assertFalse(fact.isUrl());
    }

    @SmallTest
    public void testMdFactEqualsHash() {
        String label1 = RandomString.nextWord();
        String value1 = RandomString.nextWord();
        String label2 = RandomString.nextWord();
        String value2 = RandomString.nextWord();
        ReviewId id2 = ReviewId.generateId();

        MdFactList.MdFact fact1 = new MdFactList.MdFact(label1, value1, ID);

        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(label1, value2, ID), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(label2, value1, ID), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(label1, value1, id2), false);
        MdDataUtils.testEqualsHash(fact1, new MdFactList.MdFact(label1, value1, ID), true);
    }
}
