/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 June, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.MdData;
import com.chdryra.android.reviewer.Model.ReviewData.MdDataList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 08/06/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class MdDataListTest extends TestCase {
    private static final ReviewId ID = RandomReviewId.nextId();
    private MdDataList<TestDatum> mData;

    @SmallTest
    public void testGetReviewId() {
        assertEquals(ID, mData.getReviewId());
    }

    @SmallTest
    public void testHasData() {
        assertFalse(mData.hasData());
        mData.add(new TestDatum());
        assertTrue(mData.hasData());
    }

    @SmallTest
    public void testEqualsHash() {
        TestDatum datum1 = new TestDatum();
        TestDatum datum2 = new TestDatum();
        TestDatum datum3 = new TestDatum();
        TestDatum datum4 = new TestDatum();

        mData.add(datum1);
        mData.add(datum2);
        mData.add(datum3);

        MdDataList<TestDatum> newData = new MdDataList<>(ID);
        assertFalse(mData.equals(newData));
        assertFalse(mData.hashCode() == newData.hashCode());
        newData.add(datum1);
        assertFalse(mData.equals(newData));
        assertFalse(mData.hashCode() == newData.hashCode());
        newData.add(datum2);
        assertFalse(mData.equals(newData));
        assertFalse(mData.hashCode() == newData.hashCode());
        newData.add(datum3);
        assertTrue(mData.equals(newData));
        assertTrue(mData.hashCode() == newData.hashCode());
        newData.add(datum4);
        assertFalse(mData.equals(newData));
        assertFalse(mData.hashCode() == newData.hashCode());
    }

    @Override
    protected void setUp() throws Exception {
        mData = new MdDataList<>(ID);
    }

    private static class TestDatum implements MdData {
        private ReviewId mId;

        public TestDatum() {
            mId = RandomReviewId.nextId();
        }

        @Override
        public ReviewId getReviewId() {
            return mId;
        }

        @Override
        public boolean hasData() {
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TestDatum)) return false;

            TestDatum testDatum = (TestDatum) o;

            return mId.equals(testDatum.mId);

        }

        @Override
        public int hashCode() {
            return mId.hashCode();
        }
    }
}
