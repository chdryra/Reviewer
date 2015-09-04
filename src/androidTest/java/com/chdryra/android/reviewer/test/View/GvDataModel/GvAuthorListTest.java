/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorListTest extends TestCase {
    private static final int NUM = 50;
    private GvAuthorList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvAuthorList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newAuthor(null));
        ParcelableTester.testParcelable(GvDataMocker.newAuthor(RandomReviewId
                .nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newAuthorList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newAuthorList(10, true));
    }

    @SmallTest
    public void testGvAuthor() {
        GvAuthorList.GvAuthor author1 = GvDataMocker.newAuthor(null);
        String name1 = author1.getName();
        String id1 = author1.getUserId();
        GvAuthorList.GvAuthor author2 = GvDataMocker.newAuthor(null);
        String name2 = author2.getName();
        String id2 = author2.getUserId();
        GvAuthorList.GvAuthor author3 = new GvAuthorList.GvAuthor(RandomReviewId.nextGvReviewId()
                , name1, id1);

        GvAuthorList.GvAuthor gvAuthor = new GvAuthorList.GvAuthor(name1, id1);
        GvAuthorList.GvAuthor gvAuthorEquals = new GvAuthorList.GvAuthor(name1, id1);
        GvAuthorList.GvAuthor gvAuthorEquals2 = new GvAuthorList.GvAuthor(gvAuthor);
        GvAuthorList.GvAuthor gvAuthorNotEquals1 = new GvAuthorList.GvAuthor(name2, id1);
        GvAuthorList.GvAuthor gvAuthorNotEquals2 = new GvAuthorList.GvAuthor(name2, id2);
        GvAuthorList.GvAuthor gvAuthorNotEquals3 = new GvAuthorList.GvAuthor(name1, id2);
        GvAuthorList.GvAuthor gvAuthorNotEquals4 = new GvAuthorList.GvAuthor(RandomReviewId
                .nextGvReviewId(), name1, id1);
        GvAuthorList.GvAuthor gvAuthorNull = new GvAuthorList.GvAuthor();
        GvAuthorList.GvAuthor gvAuthorEmpty1 = new GvAuthorList.GvAuthor(null, "");
        GvAuthorList.GvAuthor gvAuthorEmpty2 = new GvAuthorList.GvAuthor("", null);
        GvAuthorList.GvAuthor gvAuthorEmpty3 = new GvAuthorList.GvAuthor("", "");

        assertNotNull(gvAuthor.getViewHolder());
        assertTrue(gvAuthor.isValidForDisplay());

        assertEquals(name1, gvAuthor.getName());

        assertTrue(gvAuthor.equals(gvAuthorEquals));
        assertTrue(gvAuthor.equals(gvAuthorEquals2));
        assertFalse(gvAuthor.equals(gvAuthorNotEquals1));
        assertFalse(gvAuthor.equals(gvAuthorNotEquals2));
        assertFalse(gvAuthor.equals(gvAuthorNotEquals3));
        assertFalse(gvAuthor.equals(gvAuthorNotEquals4));

        assertFalse(gvAuthorNull.isValidForDisplay());
        assertFalse(gvAuthorEmpty1.isValidForDisplay());
        assertFalse(gvAuthorEmpty2.isValidForDisplay());
        assertFalse(gvAuthorEmpty3.isValidForDisplay());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newAuthor(null));
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvAuthorList.GvAuthor prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvAuthorList.GvAuthor next = mList.getItem(i);
            assertTrue(prev.getName().compareToIgnoreCase(next.getName()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newAuthorList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildReviewList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvAuthorList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvAuthorList list = new GvAuthorList();
        GvAuthorList list2 = new GvAuthorList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addList(mList);
        list2.addList(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvAuthorList();
    }
}
