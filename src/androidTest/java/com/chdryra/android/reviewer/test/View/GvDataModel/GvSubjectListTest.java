/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 22 June, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvComment;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCriterion;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFact;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImage;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocation;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubjectList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvText;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvUrl;
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
        assertEquals(GvSubject.TYPE, mList.getGvDataType());
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
        String subject1 = GvDataMocker.newSubject(null).getString();
        String subject2 = GvDataMocker.newSubject(null).getString();

        GvSubject gvSubject = new GvSubject(subject1);
        GvSubject gvSubjectEquals = new GvSubject(subject1);
        GvSubject gvSubjectEquals2 = new GvSubject(gvSubject);
        GvSubject gvSubjectNotEquals = new GvSubject(subject2);
        GvSubject gvSubjectNotEquals2 = new GvSubject(RandomReviewId
                .nextGvReviewId(), subject1);
        GvSubject gvSubjectNull = new GvSubject();
        GvSubject gvSubjectEmpty = new GvSubject("");

        assertNotNull(gvSubject.getViewHolder());
        assertTrue(gvSubject.isValidForDisplay());

        assertEquals(subject1, gvSubject.getString());

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
            assertTrue(prev.getString().compareToIgnoreCase(next.getString()) < 0);
            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newSubjectList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvSubject.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvSubjectList list = new GvSubjectList();
        GvSubjectList list2 = new GvSubjectList(mList);
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
        mList = new GvSubjectList();
    }
}
