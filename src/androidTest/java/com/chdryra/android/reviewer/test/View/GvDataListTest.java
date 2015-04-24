/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewId;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewId;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataListTest extends TestCase {
    private static final int    NUM  = 10;
    private static final Random RAND = new Random();

    private GvDataList<GvCommentList.GvComment> mList;

    @SmallTest
    public void testNoReviewIdConstructor() {
        mList.add(newData(null));

        testAdd(null, mList);
        testAddIterable(null, mList);
        testRemove(mList);
        testRemoveAll(mList);
        testIteratorRemove(mList);
    }

    @SmallTest
    public void testReviewIdConstructor() {
        GvReviewId id = new GvReviewId(ReviewId.generateId());
        mList.add(newData(id));
        GvDataList<GvCommentList.GvComment> idList = new GvDataList<>(id, mList);

        testAdd(id, idList);
        testAddIterable(id, idList);
        testRemove(idList);
        testRemoveAll(idList);
        testIteratorRemove(idList);
    }

    @SmallTest
    public void testReviewIdConstructorDifferentIds() {
        GvReviewId id = new GvReviewId(ReviewId.generateId());
        ArrayList<GvCommentList.GvComment> noIds = newData(null);
        ArrayList<GvCommentList.GvComment> withIds = newData(id);
        mList.add(noIds);
        mList.add(withIds);

        GvDataList<GvCommentList.GvComment> idList = new GvDataList<>(id, mList);

        assertEquals(withIds.size(), idList.size());
        for (int i = 0; i < withIds.size(); ++i) {
            assertEquals(withIds.get(i), idList.getItem(i));
        }
    }

    @SmallTest
    public void testParcelable() {
        mList.add(newData(null));
        GvDataParcelableTester.testParcelable(mList);

        GvReviewId id = new GvReviewId(ReviewId.generateId());
        mList.removeAll();
        mList.add(newData(id));
        GvDataList<GvCommentList.GvComment> idList = new GvDataList<>(id, mList);
        GvDataParcelableTester.testParcelable(idList);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvDataList<>(null, GvCommentList.GvComment.class, GvCommentList.TYPE);
    }

    private void testAdd(GvReviewId id, GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();

        ArrayList<GvCommentList.GvComment> current = list.toArrayList();
        ArrayList<GvCommentList.GvComment> comments = addData(id, list);

        if (id == null) {
            assertEquals(size + NUM, list.size());
            for (int i = 0; i < list.size(); ++i) {
                if (i < size) {
                    assertEquals(current.get(i), list.getItem(i));
                } else {
                    assertEquals(comments.get(i - size), list.getItem(i));
                }
            }
        } else {
            assertEquals(size, list.size());
        }
    }

    private void testAddIterable(GvReviewId id, GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();

        ArrayList<GvCommentList.GvComment> current = list.toArrayList();
        ArrayList<GvCommentList.GvComment> comments = newData(id);
        list.add(comments);

        if (id == null) {
            assertEquals(size + NUM, list.size());
            for (int i = 0; i < list.size(); ++i) {
                if (i < size) {
                    assertEquals(current.get(i), list.getItem(i));
                } else {
                    assertEquals(comments.get(i - size), list.getItem(i));
                }
            }
        } else {
            assertEquals(size, list.size());
        }
    }

    private void testRemove(GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();
        int index = RAND.nextInt(size - 1);
        GvCommentList.GvComment comment = list.getItem(index);
        assertTrue(list.contains(comment));

        list.remove(comment);

        if (!list.hasReviewId()) {
            assertEquals(size - 1, list.size());
            assertFalse(list.contains(comment));
        } else {
            assertEquals(size, list.size());
            assertTrue(list.contains(comment));
        }
    }

    private void testRemoveAll(GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();

        list.removeAll();

        if (!list.hasReviewId()) {
            assertEquals(0, list.size());
        } else {
            assertEquals(size, list.size());
        }
    }

    private void testIteratorRemove(GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }

        if (!list.hasReviewId()) {
            assertEquals(0, list.size());
        } else {
            assertEquals(size, list.size());
        }
    }

    private ArrayList<GvCommentList.GvComment> addData(GvReviewId id,
            GvDataList<GvCommentList.GvComment> list) {
        ArrayList<GvCommentList.GvComment> comments = newData(id);
        for (int i = 0; i < NUM; ++i) {
            list.add(comments.get(i));
        }

        return comments;
    }

    private ArrayList<GvCommentList.GvComment> newData(GvReviewId id) {
        ArrayList<GvCommentList.GvComment> comments = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            GvCommentList.GvComment comment = id != null ?
                    new GvCommentList.GvComment(id, RandomString.nextSentence()) :
                    new GvCommentList.GvComment(RandomString.nextSentence());
            comments.add(comment);
        }

        return comments;
    }
}
