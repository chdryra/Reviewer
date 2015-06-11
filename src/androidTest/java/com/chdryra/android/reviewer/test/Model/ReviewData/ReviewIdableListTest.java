/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewData;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewIdableListTest extends TestCase {
    private final static int NUM = 100;
    private ReviewIdableList<Idable> mCollection;

    @SmallTest
    public void testAddContainsIdSizeGet() {
        assertEquals(0, mCollection.size());
        Idable[] data = new Idable[NUM];
        for (int i = 0; i < NUM; ++i) {
            data[i] = new Idable();
            ReviewId id = data[i].getId();
            assertFalse(mCollection.containsId(id));
            mCollection.add(data[i]);
            assertTrue(mCollection.containsId(id));
            assertEquals(i + 1, mCollection.size());
            assertEquals(data[i], mCollection.get(id));
        }

        for (int i = 0; i < NUM; ++i) {
            assertEquals(data[i], mCollection.getItem(i));
        }
    }

    @SmallTest
    public void testAddList() {
        assertEquals(0, mCollection.size());
        for (int i = 0; i < NUM; ++i) {
            mCollection.add(new Idable());
        }
        assertEquals(NUM, mCollection.size());

        ReviewIdableList<Idable> collection = new ReviewIdableList<>();
        assertEquals(0, collection.size());
        collection.add(mCollection);
        assertEquals(NUM, collection.size());
        for (int i = 0; i < NUM; ++i) {
            assertEquals(mCollection.getItem(i), collection.getItem(i));
        }
    }

    @SmallTest
    public void testRemove() {
        assertEquals(0, mCollection.size());
        for (int i = 0; i < NUM - 1; ++i) {
            mCollection.add(new Idable());
        }
        Idable toRemove = new Idable();
        mCollection.add(toRemove);

        assertEquals(NUM, mCollection.size());
        assertTrue(mCollection.containsId(toRemove.getId()));
        mCollection.remove(toRemove.getId());
        assertEquals(NUM - 1, mCollection.size());
        assertFalse(mCollection.containsId(toRemove.getId()));
    }

    @SmallTest
    public void testEquals() {
        assertEquals(0, mCollection.size());
        for (int i = 0; i < NUM; ++i) {
            mCollection.add(new Idable());
        }
        assertEquals(NUM, mCollection.size());

        ReviewIdableList<Idable> collection = new ReviewIdableList<>();
        collection.add(mCollection);
        assertTrue(mCollection.equals(collection));
        Idable newIdable = new Idable();
        collection.add(newIdable);
        assertFalse(mCollection.equals(collection));
        collection.remove(newIdable.getId());
        assertTrue(mCollection.equals(collection));
        collection.remove(collection.getItem(NUM / 2).getId());
        assertFalse(mCollection.equals(collection));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCollection = new ReviewIdableList<>();
    }

    private static class Idable implements ReviewId.ReviewIdAble {
        private ReviewId mId;

        public Idable() {
            mId = RandomReviewId.nextId();
        }

        @Override
        public ReviewId getId() {
            return mId;
        }
    }
}
