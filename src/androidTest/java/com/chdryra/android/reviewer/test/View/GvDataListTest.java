/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewId;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
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
        mList.addList(newData(null));

        testAdd(null, mList);
        testAddIterable(null, mList);
        testRemove(mList);
        testRemoveAll(mList);
        testIteratorRemove(mList);
    }

    @SmallTest
    public void testReviewIdConstructor() {
        GvReviewId id = RandomReviewId.nextGvReviewId();
        mList.addList(newData(id));
        GvDataList<GvCommentList.GvComment> idList = new GvDataList<>(id, mList);

        testAdd(id, idList);
        testAddIterable(id, idList);
        testRemove(idList);
        testRemoveAll(idList);
        testIteratorRemove(idList);
    }

    @SmallTest
    public void testParcelable() {
        mList.addList(newData(null));
        GvDataParcelableTester.testParcelable(mList);

        GvReviewId id = RandomReviewId.nextGvReviewId();
        mList.removeAll();
        mList.addList(newData(id));
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

        assertEquals(size + NUM, list.size());
        for (int i = 0; i < list.size(); ++i) {
            if (i < size) {
                assertEquals(current.get(i), list.getItem(i));
            } else {
                assertEquals(comments.get(i - size), list.getItem(i));
            }
        }
    }

    private void testAddIterable(GvReviewId id, GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();

        ArrayList<GvCommentList.GvComment> current = list.toArrayList();
        ArrayList<GvCommentList.GvComment> comments = newData(id);
        list.addList(comments);

        assertEquals(size + NUM, list.size());
        for (int i = 0; i < list.size(); ++i) {
            if (i < size) {
                assertEquals(current.get(i), list.getItem(i));
            } else {
                assertEquals(comments.get(i - size), list.getItem(i));
            }
        }
    }

    private void testRemove(GvDataList<GvCommentList.GvComment> list) {
        int size = list.size();
        int index = RAND.nextInt(size - 1);
        GvCommentList.GvComment comment = list.getItem(index);
        assertTrue(list.contains(comment));

        list.remove(comment);

        assertEquals(size - 1, list.size());
        assertFalse(list.contains(comment));
    }

    private void testRemoveAll(GvDataList<GvCommentList.GvComment> list) {
        list.removeAll();
        assertEquals(0, list.size());
    }

    private void testIteratorRemove(GvDataList<GvCommentList.GvComment> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertEquals(0, list.size());
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
