/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvSubjectList;
import com.chdryra.android.reviewer.View.GvDataModel.GvText;
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
public class GvSubjectListTest extends TestCase {
    private static final int NUM = 50;
    private GvSubjectList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvSubjectList.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newSubject(null));
        ParcelableTester.testParcelable(GvDataMocker.newSubject(RandomReviewId
                .nextGvReviewId()));
        ParcelableTester.testParcelable(GvDataMocker.newSubjectList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newSubjectList(10, true));
    }

    @SmallTest
    public void testGvSubject() {
        String subject1 = GvDataMocker.newSubject(null).get();
        String subject2 = GvDataMocker.newSubject(null).get();

        GvSubjectList.GvSubject gvSubject = new GvSubjectList.GvSubject(subject1);
        GvSubjectList.GvSubject gvSubjectEquals = new GvSubjectList.GvSubject(subject1);
        GvSubjectList.GvSubject gvSubjectEquals2 = new GvSubjectList.GvSubject(gvSubject);
        GvSubjectList.GvSubject gvSubjectNotEquals = new GvSubjectList.GvSubject(subject2);
        GvSubjectList.GvSubject gvSubjectNotEquals2 = new GvSubjectList.GvSubject(RandomReviewId
                .nextGvReviewId(), subject1);
        GvSubjectList.GvSubject gvSubjectNull = new GvSubjectList.GvSubject();
        GvSubjectList.GvSubject gvSubjectEmpty = new GvSubjectList.GvSubject("");

        assertNotNull(gvSubject.getViewHolder());
        assertTrue(gvSubject.isValidForDisplay());

        assertEquals(subject1, gvSubject.get());

        assertTrue(gvSubject.equals(gvSubjectEquals));
        assertTrue(gvSubject.equals(gvSubjectEquals2));
        assertFalse(gvSubject.equals(gvSubjectNotEquals));
        assertFalse(gvSubject.equals(gvSubjectNotEquals2));

        assertFalse(gvSubjectNull.isValidForDisplay());
        assertFalse(gvSubjectEmpty.isValidForDisplay());
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newSubject(null));
        }

        assertEquals(50, mList.size());

        mList.sort();
        GvText prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvText next = mList.getItem(i);
            assertTrue(prev.get().compareToIgnoreCase(next.get()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addList(GvDataMocker.newSubjectList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildReviewList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvSubjectList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvSubjectList list = new GvSubjectList();
        GvSubjectList list2 = new GvSubjectList(mList);
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
        mList = new GvSubjectList();
    }
}
