/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.startouch.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvComment;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTagList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ParcelableTester;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;

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
        assertEquals(GvTag.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newTag(null));
        ParcelableTester.testParcelable(GvDataMocker.newTag(RandomReviewId.nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newTagList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newTagList(10, true));
    }

    @SmallTest
    public void testGvTag() {
        String tag1 = GvDataMocker.newTag(null).getString();
        String tag2 = GvDataMocker.newTag(null).getString();

        GvTag gvTag = new GvTag(tag1);
        GvTag gvTagEquals = new GvTag(tag1);
        GvTag gvTagEquals2 = new GvTag(gvTag);
        GvTag gvTagNotEquals = new GvTag(tag2);
        GvTag gvTagNotEquals2 = new GvTag(RandomReviewId.nextGvReviewId(),
                tag1);
        GvTag gvTagNull = new GvTag();
        GvTag gvTagEmpty = new GvTag("");

        assertNotNull(gvTag.getViewHolder());
        assertTrue(gvTag.isValidForDisplay());

        assertEquals(tag1, gvTag.getString());

        assertTrue(gvTag.equals(gvTagEquals));
        assertTrue(gvTag.equals(gvTagEquals2));
        assertFalse(gvTag.equals(gvTagNotEquals));
        assertFalse(gvTag.equals(gvTagNotEquals2));

        assertFalse(gvTagNull.isValidForDisplay());
        assertFalse(gvTagEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newTag(null));
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvTag prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvTag next = mList.getItem(i);
            assertTrue(prev.getString().compareToIgnoreCase(next.getString()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newTagList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvTagList list = new GvTagList();
        GvTagList list2 = new GvTagList(mList);
        assertEquals(0, list.size());
        for (int i = 0; i < mList.size(); ++i) {
            assertFalse(mList.equals(list));
            list.add(mList.getItem(i));
        }

        assertTrue(mList.equals(list));
        assertTrue(mList.equals(list2));
        list.addAll(mList);
        list2.addAll(mList);
        assertFalse(mList.equals(list));
        assertFalse(mList.equals(list2));
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvTagList();
    }
}
