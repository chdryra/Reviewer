/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 25 March, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvReviewId;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
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
    private static final int NUM = 10;
    private static final Random RAND = new Random();

    private GvDataListImpl<GvComment> mList;

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
        GvDataListImpl<GvComment> idList = new GvDataListImpl<>(id, mList);

        testAdd(id, idList);
        testAddIterable(id, idList);
        testRemove(idList);
        testRemoveAll(idList);
        testIteratorRemove(idList);
    }

    @SmallTest
    public void testParcelable() {
        mList.addList(newData(null));
        ParcelableTester.testParcelable(mList);

        GvReviewId id = RandomReviewId.nextGvReviewId();
        mList.removeAll();
        mList.addList(newData(id));
        GvDataList<GvComment> idList = new GvDataListImpl<>(id, mList);
        ParcelableTester.testParcelable(idList);
    }

    private void testAdd(GvReviewId id, GvDataListImpl<GvComment> list) {
        int size = list.size();

        ArrayList<GvComment> current = list.toArrayList();
        ArrayList<GvComment> comments = addData(id, list);

        assertEquals(size + NUM, list.size());
        for (int i = 0; i < list.size(); ++i) {
            if (i < size) {
                assertEquals(current.get(i), list.getItem(i));
            } else {
                assertEquals(comments.get(i - size), list.getItem(i));
            }
        }
    }

    private void testAddIterable(GvReviewId id, GvDataListImpl<GvComment> list) {
        int size = list.size();

        ArrayList<GvComment> current = list.toArrayList();
        ArrayList<GvComment> comments = newData(id);
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

    private void testRemove(GvDataListImpl<GvComment> list) {
        int size = list.size();
        int index = RAND.nextInt(size - 1);
        GvComment comment = list.getItem(index);
        assertTrue(list.contains(comment));

        list.remove(comment);

        assertEquals(size - 1, list.size());
        assertFalse(list.contains(comment));
    }

    private void testRemoveAll(GvDataListImpl<GvComment> list) {
        assertTrue(list.size() > 0);
        list.removeAll();
        assertEquals(0, list.size());
    }

    private void testIteratorRemove(GvDataList<GvComment> list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        assertEquals(0, list.size());
    }

    private ArrayList<GvComment> addData(GvReviewId id,
                                                       GvDataListImpl<GvComment> list) {
        ArrayList<GvComment> comments = newData(id);
        for (int i = 0; i < NUM; ++i) {
            list.add(comments.get(i));
        }

        return comments;
    }

    private ArrayList<GvComment> newData(GvReviewId id) {
        ArrayList<GvComment> comments = new ArrayList<>();
        for (int i = 0; i < NUM; ++i) {
            GvComment comment = id != null ?
                    new GvComment(id, RandomString.nextSentence()) :
                    new GvComment(RandomString.nextSentence());
            comments.add(comment);
        }

        return comments;
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvDataListImpl<>(GvComment.TYPE, null);
    }
}
