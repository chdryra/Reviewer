/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 21 January, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.RCollection;
import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.testutils.ExceptionTester;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import org.apache.commons.lang3.ArrayUtils;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RCollectionTest extends TestCase {
    private final static int NUM = 100;
    private RCollection<String> mCollection;

    @SmallTest
    public void testPutGetSize() {
        assertEquals(0, mCollection.size());
        String[] data = new String[NUM];
        for (int i = 0; i < NUM; ++i) {
            data[i] = RandomString.nextWord();
            mCollection.put(ReviewId.generateId(), data[i]);
            assertEquals(i + 1, mCollection.size());
        }

        for (int i = 0; i < NUM; ++i) {
            assertEquals(data[i], mCollection.getItem(i));
        }
    }

    @SmallTest
    public void testContainsId() {
        ReviewId id = ReviewId.generateId();

        for (int i = 0; i < NUM; ++i) {
            mCollection.put(ReviewId.generateId(), RandomString.nextWord());
            assertFalse(mCollection.containsId(id));
        }

        mCollection.put(id, RandomString.nextWord());
        assertTrue(mCollection.containsId(id));
    }

    @SmallTest
    public void testAdd() {
        assertEquals(0, mCollection.size());
        RCollection<String> collection = new RCollection<>();
        String[] data = new String[NUM];
        for (int i = 0; i < NUM; ++i) {
            data[i] = RandomString.nextWord();
            collection.put(ReviewId.generateId(), data[i]);
        }

        assertEquals(0, mCollection.size());
        assertEquals(NUM, collection.size());
        mCollection.add(collection);
        assertEquals(NUM, mCollection.size());

        for (int i = 0; i < NUM; ++i) {
            assertEquals(data[i], mCollection.getItem(i));
        }
    }

    @SmallTest
    public void testRemove() {
        ReviewId[] ids = new ReviewId[NUM];
        String[] data = new String[NUM];
        for (int i = 0; i < NUM; ++i) {
            ids[i] = ReviewId.generateId();
            data[i] = RandomString.nextWord();
            mCollection.put(ids[i], data[i]);
        }

        assertEquals(NUM, mCollection.size());
        for (int i = 0; i < NUM; ++i) {
            assertTrue(mCollection.containsId(ids[i]));
            assertEquals(data[i], mCollection.getItem(i));
        }

        assertEquals(NUM, mCollection.size());
        Random rand = new Random();
        for (int i = 0; i < 10; ++i) {
            ReviewId toDelete = ids[rand.nextInt(ids.length)];
            assertTrue(mCollection.containsId(toDelete));
            mCollection.remove(toDelete);
            assertEquals(NUM - i - 1, mCollection.size());
            assertFalse(mCollection.containsId(toDelete));
            ArrayUtils.removeElement(ids, toDelete);
        }
    }

    @SmallTest
    public void testRCollectionIterator() {
        ReviewId[] ids = new ReviewId[NUM];
        String[] data = new String[NUM];
        for (int i = 0; i < NUM; ++i) {
            ids[i] = ReviewId.generateId();
            data[i] = RandomString.nextWord();
            mCollection.put(ids[i], data[i]);
        }

        Iterator<String> it = mCollection.iterator();
        assertNotNull(it);

        for (int i = 0; i < NUM; ++i) {
            assertTrue(it.hasNext());
            assertEquals(data[i], it.next());
        }
        assertFalse(it.hasNext());
        ExceptionTester.test(it, "next", NoSuchElementException.class, "No more elements left");

        int i = 0;
        for (String string : mCollection) {
            assertEquals(data[i++], string);
        }

        it = mCollection.iterator();
        ExceptionTester.test(it, "remove", IllegalStateException.class,
                "Have to do at least one next() before you can delete");
        for (i = 0; i < 10; ++i) {
            ReviewId id = ids[i];
            assertTrue(mCollection.containsId(id));
            assertEquals(NUM - i, mCollection.size());
            it.next();
            it.remove();
            assertEquals(NUM - i - 1, mCollection.size());
            assertFalse(mCollection.containsId(id));
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mCollection = new RCollection<>();
    }
}
