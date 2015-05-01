/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvChildList;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.View.GvTagList;
import com.chdryra.android.reviewer.View.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.GvDataParcelableTester;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTagListTest extends TestCase {
    private static final int NUM = 50;
    private GvTagList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvTagList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        GvDataParcelableTester.testParcelable(GvDataMocker.newTag());
        GvDataParcelableTester.testParcelable(GvDataMocker.newTagList(10));
    }

    @SmallTest
    public void testGvTag() {
        String tag1 = GvDataMocker.newTag().get();
        String tag2 = GvDataMocker.newTag().get();

        GvTagList.GvTag gvTag = new GvTagList.GvTag(tag1);
        GvTagList.GvTag gvTagEquals = new GvTagList.GvTag(tag1);
        GvTagList.GvTag gvTagEquals2 = new GvTagList.GvTag(gvTag);
        GvTagList.GvTag gvTagNotEquals = new GvTagList.GvTag(tag2);
        GvTagList.GvTag gvTagNull = new GvTagList.GvTag();
        GvTagList.GvTag gvTagEmpty = new GvTagList.GvTag("");

        assertNotNull(gvTag.newViewHolder());
        assertTrue(gvTag.isValidForDisplay());

        assertEquals(tag1, gvTag.get());

        assertTrue(gvTag.equals(gvTagEquals));
        assertTrue(gvTag.equals(gvTagEquals2));
        assertFalse(gvTag.equals(gvTagNotEquals));

        assertFalse(gvTagNull.isValidForDisplay());
        assertFalse(gvTagEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newTag());
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvTagList.GvTag prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvTagList.GvTag next = mList.getItem(i);
            assertTrue(prev.get().compareToIgnoreCase(next.get()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.add(GvDataMocker.newTagList(NUM));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvTagList list = new GvTagList();
        GvTagList list2 = new GvTagList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.add(mList);
        list2.add(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvTagList();
    }
}
