/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvTagList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvTagListTest extends TestCase {
    private GvTagList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvTagList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testGvTag() {
        String tag1 = GvDataMocker.newTag().get();
        String tag2 = GvDataMocker.newTag().get();

        GvTagList.GvTag gvTag = new GvTagList.GvTag(tag1);
        GvTagList.GvTag gvTagEquals = new GvTagList.GvTag(tag1);
        GvTagList.GvTag gvTagNotEquals = new GvTagList.GvTag(tag2);
        GvTagList.GvTag gvTagNull = new GvTagList.GvTag();
        GvTagList.GvTag gvTagEmpty = new GvTagList.GvTag("");

        assertNotNull(gvTag.newViewHolder());
        assertTrue(gvTag.isValidForDisplay());

        assertEquals(tag1, gvTag.get());

        assertTrue(gvTag.equals(gvTagEquals));
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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvTagList();
    }
}