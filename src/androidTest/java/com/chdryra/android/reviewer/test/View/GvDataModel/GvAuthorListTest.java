/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthor;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvAuthorList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvComment;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvCriterion;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvFact;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvImage;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvLocation;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvUrl;
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
        assertEquals(GvAuthor.TYPE, mList.getGvDataType());
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
        GvAuthor author1 = GvDataMocker.newAuthor(null);
        String name1 = author1.getName();
        String id1 = author1.getUserId();
        GvAuthor author2 = GvDataMocker.newAuthor(null);
        String name2 = author2.getName();
        String id2 = author2.getUserId();

        GvAuthor gvAuthor = new GvAuthor(name1, id1);
        GvAuthor gvAuthorEquals = new GvAuthor(name1, id1);
        GvAuthor gvAuthorEquals2 = new GvAuthor(gvAuthor);
        GvAuthor gvAuthorNotEquals1 = new GvAuthor(name2, id1);
        GvAuthor gvAuthorNotEquals2 = new GvAuthor(name2, id2);
        GvAuthor gvAuthorNotEquals3 = new GvAuthor(name1, id2);
        GvAuthor gvAuthorNotEquals4 = new GvAuthor(RandomReviewId
                .nextGvReviewId(), name1, id1);
        GvAuthor gvAuthorNull = new GvAuthor();
        GvAuthor gvAuthorEmpty1 = new GvAuthor(null, "");
        GvAuthor gvAuthorEmpty2 = new GvAuthor("", null);
        GvAuthor gvAuthorEmpty3 = new GvAuthor("", "");

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
        GvAuthor prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvAuthor next = mList.getItem(i);
            assertTrue(prev.getName().compareToIgnoreCase(next.getName()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newAuthorList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvAuthor.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

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

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvAuthorList();
    }
}
