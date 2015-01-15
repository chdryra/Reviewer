/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 15 January, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.GvChildrenList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.RatingMocker;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvChildrenListTest extends TestCase {
    private GvChildrenList mList;

    @SmallTest
    public void testGetGvType() {
        assertEquals(GvChildrenList.TYPE, mList.getGvType());
    }

    @SmallTest
    public void testContainsSubject() {
        GvChildrenList.GvChildReview child = GvDataMocker.newChild();
        assertFalse(mList.contains(child.getSubject()));
        mList.add(child);
        assertTrue(mList.contains(child.getSubject()));

        GvChildrenList.GvChildReview child2 = GvDataMocker.newChild();
        assertFalse(mList.contains(child2.getSubject()));
        mList.add(child2);
        assertTrue(mList.contains(child2.getSubject()));

        mList.remove(child);
        assertFalse(mList.contains(child.getSubject()));
    }

    @SmallTest
    public void testComparator() {
        for (int i = 0; i < 50; ++i) {
            mList.add(GvDataMocker.newChild());
        }

        assertEquals(50, mList.size());

        Random rand = new Random();
        for (int i = 0; i < 50; ++i) {
            int item = rand.nextInt(9);
            String subject = mList.getItem(item).getSubject();
            float rating = RatingMocker.nextRating();
            mList.add(new GvChildrenList.GvChildReview(subject, rating));
        }

        mList.sort();
        GvChildrenList.GvChildReview prev = mList.getItem(0);
        for (int i = 1; i < mList.size(); ++i) {
            GvChildrenList.GvChildReview next = mList.getItem(i);
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
    public void testGvChildReview() {
        GvChildrenList.GvChildReview review1 = GvDataMocker.newChild();
        GvChildrenList.GvChildReview review2 = GvDataMocker.newChild();

        String subject1 = review1.getSubject();
        float rating1 = review1.getRating();
        String subject2 = review2.getSubject();
        float rating2 = review2.getRating();

        GvChildrenList.GvChildReview gvChild = new GvChildrenList.GvChildReview(subject1, rating1);
        GvChildrenList.GvChildReview gvChildEquals = new GvChildrenList.GvChildReview(subject1,
                rating1);
        GvChildrenList.GvChildReview gvChildNotEquals1 = new GvChildrenList.GvChildReview
                (subject1, rating2);
        GvChildrenList.GvChildReview gvChildNotEquals2 = new GvChildrenList.GvChildReview
                (subject2, rating1);
        GvChildrenList.GvChildReview gvChildNotEquals3 = new GvChildrenList.GvChildReview
                (subject2, rating2);
        GvChildrenList.GvChildReview gvChildNull = new GvChildrenList.GvChildReview();
        GvChildrenList.GvChildReview gvChildEmpty = new GvChildrenList.GvChildReview("", rating1);

        assertNotNull(gvChild.newViewHolder());
        assertTrue(gvChild.isValidForDisplay());

        assertEquals(subject1, gvChild.getSubject());
        assertEquals(rating1, gvChild.getRating());

        assertTrue(gvChild.equals(gvChildEquals));
        assertFalse(gvChild.equals(gvChildNotEquals1));
        assertFalse(gvChild.equals(gvChildNotEquals2));
        assertFalse(gvChild.equals(gvChildNotEquals3));

        assertFalse(gvChildNull.isValidForDisplay());
        assertFalse(gvChildEmpty.isValidForDisplay());
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mList = new GvChildrenList();
    }
}
