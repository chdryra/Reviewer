/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test.View.GvDataModel;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.View.GvDataModel.GvChildReviewList;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.View.GvDataModel.GvTagList;
import com.chdryra.android.reviewer.View.GvDataModel.GvUrlList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.ParcelableTester;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvChildReviewListTest extends TestCase {
    private static final int NUM = 50;
    private GvChildReviewList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvChildReviewList.TYPE, mList.getGvDataType());
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
        GvChildReviewList.GvChildReview child = GvDataMocker.newChild(null);
        assertFalse(mList.contains(child.getSubject()));
        mList.add(child);
        assertTrue(mList.contains(child.getSubject()));

        GvChildReviewList.GvChildReview child2 = GvDataMocker.newChild(null);
        assertFalse(mList.contains(child2.getSubject()));
        mList.add(child2);
        assertTrue(mList.contains(child2.getSubject()));

        mList.remove(child);
        assertFalse(mList.contains(child.getSubject()));
    }

    @SmallTest
    public void testComparator() {
        mList.addList(GvDataMocker.newChildList(NUM, false));
        assertEquals(NUM, mList.size());

        Random rand = new Random();
        for (int i = 0; i < NUM; ++i) {
            int item = rand.nextInt(9);
            String subject = mList.getItem(item).getSubject();
            float rating = RandomRating.nextRating();
            mList.add(new GvChildReviewList.GvChildReview(subject, rating));
        }

        mList.sort();
        GvChildReviewList.GvChildReview prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvChildReviewList.GvChildReview next = mList.getItem(i);
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
        mList.addList(GvDataMocker.newChildList(NUM, false));
        assertEquals(NUM, mList.size());

        assertFalse(mList.equals(GvDataMocker.getData(GvChildReviewList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvTagList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvLocationList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvCommentList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvFactList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvImageList.TYPE, NUM)));
        assertFalse(mList.equals(GvDataMocker.getData(GvUrlList.TYPE, NUM)));

        GvChildReviewList list = new GvChildReviewList();
        GvChildReviewList list2 = new GvChildReviewList(mList);
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

    @SmallTest
    public void testGvChildReview() {
        GvChildReviewList.GvChildReview review1 = GvDataMocker.newChild(null);
        GvChildReviewList.GvChildReview review2 = GvDataMocker.newChild(null);

        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();
        while (rating1 == rating2) {
            review2 = GvDataMocker.newChild(null);
            rating2 = review2.getRating();
        }

        GvChildReviewList.GvChildReview gvChild = new GvChildReviewList.GvChildReview(subject1,
                rating1);
        GvChildReviewList.GvChildReview gvChildEquals = new GvChildReviewList.GvChildReview
                (subject1,
                rating1);
        GvChildReviewList.GvChildReview gvChildEquals2 = new GvChildReviewList.GvChildReview
                (gvChild);
        GvChildReviewList.GvChildReview gvChildNotEquals1 = new GvChildReviewList.GvChildReview
                (subject1, rating2);
        GvChildReviewList.GvChildReview gvChildNotEquals2 = new GvChildReviewList.GvChildReview
                (subject2, rating1);
        GvChildReviewList.GvChildReview gvChildNotEquals3 = new GvChildReviewList.GvChildReview
                (subject2, rating2);
        GvChildReviewList.GvChildReview gvChildNotEquals4 = new GvChildReviewList.GvChildReview
                (RandomReviewId.nextGvReviewId(), subject1, rating1);
        GvChildReviewList.GvChildReview gvChildNull = new GvChildReviewList.GvChildReview();
        GvChildReviewList.GvChildReview gvChildEmpty = new GvChildReviewList.GvChildReview("", rating1);

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

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvChildReviewList();
    }
}
