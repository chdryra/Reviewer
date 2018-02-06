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
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvCriterionList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvFact;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvImage;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvLocation;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvTag;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData.GvUrl;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.ParcelableTester;
import com.chdryra.android.startouch.test.TestUtils.RandomRating;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvCriterionListTest extends TestCase {
    private static final int NUM = 50;
    private GvCriterionList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvCriterion.TYPE, mList.getGvDataType());
    }

    @SmallTest
    public void testParcelable() {
        ParcelableTester.testParcelable(GvDataMocker.newChild(null));
        ParcelableTester.testParcelable(GvDataMocker.newChild(RandomReviewId.nextGvReviewId
                ()));
        ParcelableTester.testParcelable(GvDataMocker.newChildList(10, false));
        ParcelableTester.testParcelable(GvDataMocker.newChildList(10, true));
    }

    @SmallTest
    public void testContainsSubject() {
        GvCriterion child = GvDataMocker.newChild(null);
        assertFalse(mList.contains(child.getSubject()));
        mList.add(child);
        assertTrue(mList.contains(child.getSubject()));

        GvCriterion child2 = GvDataMocker.newChild(null);
        assertFalse(mList.contains(child2.getSubject()));
        mList.add(child2);
        assertTrue(mList.contains(child2.getSubject()));

        mList.remove(child);
        assertFalse(mList.contains(child.getSubject()));
    }

    @SmallTest
    public void testComparator() {
        mList.addAll(GvDataMocker.newChildList(NUM, false));
        assertEquals(NUM, mList.size());

        Random rand = new Random();
        for (int i = 0; i < NUM; ++i) {
            int item = rand.nextInt(9);
            String subject = mList.getItem(item).getSubject();
            float rating = RandomRating.nextRating();
            mList.add(new GvCriterion(subject, rating));
        }

        mList.sort();
        GvCriterion prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvCriterion next = mList.getItem(i);
            String prevSubject = prev.getSubject();
            float prevRating = prev.getRating();
            String nextSubject = next.getSubject();
            float nextRating = next.getRating();
            if (nextSubject.equals(prevSubject)) {
                assertTrue(prevRating >= nextRating);
            } else {
                assertTrue(prevSubject.compareTo(nextSubject) < 0);
            }

            prev = next;
        }
    }

    @SmallTest
    public void testEquals() {
        mList.addAll(GvDataMocker.newChildList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvCriterion.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTag.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocation.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvComment.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFact.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImage.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrl.TYPE, NUM)));

        GvCriterionList list = new GvCriterionList();
        GvCriterionList list2 = new GvCriterionList(mList);
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

    @SmallTest
    public void testGvChildReview() {
        GvCriterion review1 = GvDataMocker.newChild(null);
        GvCriterion review2 = GvDataMocker.newChild(null);

        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        while (rating1 == rating2) {
            review2 = GvDataMocker.newChild(null);
            rating2 = review2.getRating();
        }

        GvCriterion gvChild = new GvCriterion(subject1,
                rating1);
        GvCriterion gvChildEquals = new GvCriterion
                (subject1,
                        rating1);
        GvCriterion gvChildEquals2 = new GvCriterion
                (gvChild);
        GvCriterion gvChildNotEquals1 = new GvCriterion
                (subject1, rating2);
        GvCriterion gvChildNotEquals2 = new GvCriterion
                (subject2, rating1);
        GvCriterion gvChildNotEquals3 = new GvCriterion
                (subject2, rating2);
        GvCriterion gvChildNotEquals4 = new GvCriterion
                (RandomReviewId.nextGvReviewId(), subject1, rating1);
        GvCriterion gvChildNull = new GvCriterion();
        GvCriterion gvChildEmpty = new GvCriterion("", rating1);

        assertNotNull(gvChild.getViewHolder());
        assertTrue(gvChild.isValidForDisplay());

        assertEquals(subject1, gvChild.getSubject());
        assertEquals(rating1, gvChild.getRating());

        assertTrue(gvChild.equals(gvChildEquals));
        assertTrue(gvChild.equals(gvChildEquals2));
        assertFalse(gvChild.equals(gvChildNotEquals1));
        assertFalse(gvChild.equals(gvChildNotEquals2));
        assertFalse(gvChild.equals(gvChildNotEquals3));
        assertFalse(gvChild.equals(gvChildNotEquals4));

        assertFalse(gvChildNull.isValidForDisplay());
        assertFalse(gvChildEmpty.isValidForDisplay());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvCriterionList();
    }
}
