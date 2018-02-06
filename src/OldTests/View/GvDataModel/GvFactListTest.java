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
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterion;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ParcelableTester;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvFactListTest extends TestCase {
    private static final int NUM = 50;
    private GvFactList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvFact.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newFact(null));
        ParcelableTester.testParcelable(GvDataMocker.newFact(RandomReviewId.nextGvReviewId
                ()));
        ParcelableTester.testParcelable(GvDataMocker.newFactList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newFactList(10, true));
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newFact(null));
        }

        assertEquals(50, mList.size());

        Random rand = new Random();
        for (int i = 0; i < 50; ++i) {
            int item = rand.nextInt(9);
            String label = mList.getItem(item).getLabel();
            String value = RandomString.nextWord();
            mList.add(new GvFact(label, value));
        }

        mList.sort();
        GvFact prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvFact next = mList.getItem(i);
            String prevLabel = prev.getLabel();
            String prevValue = prev.getValue();
            String nextLabel = next.getLabel();
            String nextValue = next.getValue();
            if (nextLabel.equals(prevLabel)) {
                assertTrue(prevValue.compareTo(nextValue) <= 0);
            } else {
                assertTrue(prevLabel.compareTo(nextLabel) < 0);
            }

            prev = next;
        }
    }

    @SmallTest
    public void testGvFact() {
        GvFact fact1 = GvDataMocker.newFact(null);
        GvFact fact2 = GvDataMocker.newFact(null);

        String label1 = fact1.getLabel();
        String value1 = fact1.getValue();
        String label2 = fact2.getLabel();
        String value2 = fact2.getValue();

        GvFact gvFact = new GvFact(label1, value1);
        GvFact gvFactEquals = new GvFact(label1,
                value1);
        GvFact gvFactEquals2 = new GvFact(gvFact);
        GvFact gvFactNotEquals1 = new GvFact
                (label1, value2);
        GvFact gvFactNotEquals2 = new GvFact
                (label2, value1);
        GvFact gvFactNotEquals3 = new GvFact
                (label2, value2);
        GvFact gvFactNotEquals4 = new GvFact
                (RandomReviewId.nextGvReviewId(), label1, value1);
        GvFact gvFactNull = new GvFact();
        GvFact gvFactEmpty1 = new GvFact(label1, "");
        GvFact gvFactEmpty2 = new GvFact("", value1);
        GvFact gvFactEmpty3 = new GvFact("", "");

        assertNotNull(gvFact.getViewHolder());
        assertTrue(gvFact.isValidForDisplay());

        assertEquals(label1, gvFact.getLabel());
        assertEquals(value1, gvFact.getValue());

        assertTrue(gvFact.equals(gvFactEquals));
        assertTrue(gvFact.equals(gvFactEquals2));
        assertFalse(gvFact.equals(gvFactNotEquals1));
        assertFalse(gvFact.equals(gvFactNotEquals2));
        assertFalse(gvFact.equals(gvFactNotEquals3));
        assertFalse(gvFact.equals(gvFactNotEquals4));

        assertFalse(gvFactNull.isValidForDisplay());
        assertFalse(gvFactEmpty1.isValidForDisplay());
        assertFalse(gvFactEmpty2.isValidForDisplay());
        assertFalse(gvFactEmpty3.isValidForDisplay());
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newFactList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvFactList list = new GvFactList();
        GvFactList list2 = new GvFactList(mList);
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
        mList = new GvFactList();
    }
}
