/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.ControllerReviewCollection;
import com.chdryra.android.reviewer.GvDataList;
import com.chdryra.android.reviewer.GvOverviewList;
import com.chdryra.android.reviewer.GvSubjectRatingList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import junit.framework.TestCase;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewCollectionTest extends TestCase {
    private static final int MIN = 10;
    private static final int MAX = 20;
    private ControllerReviewCollection<Review> mController;

    @SmallTest
    public void testAddReview() {
        GvDataList list = mController.toGridViewable(false);
        GvDataList listP = mController.toGridViewable(true);
        assertNotNull(list);
        assertNotNull(listP);
        assertEquals(0, list.size());
        assertEquals(0, listP.size());

        Review[] reviews = addReviews(mController, false, getRandInt());
        Review[] reviewsP = addReviews(mController, true, getRandInt());

        list = mController.toGridViewable(false);
        listP = mController.toGridViewable(true);
        assertNotNull(list);
        assertNotNull(listP);
        assertEquals(reviews.length + reviewsP.length, list.size());
        assertEquals(reviewsP.length, listP.size());
    }

    @SmallTest
    public void testToGridViewableFalse() {
        Review[] reviews = addReviews(mController, false, getRandInt());
        Review[] reviewsP = addReviews(mController, true, getRandInt());
        GvSubjectRatingList rsList = (GvSubjectRatingList) mController.toGridViewable
                (false);
        assertNotNull(rsList);

        int rl = reviews.length;
        int rpl = reviewsP.length;
        assertEquals(rl + rpl, rsList.size());
        Review[] all = new Review[rl + rpl];
        System.arraycopy(reviews, 0, all, 0, rl);
        System.arraycopy(reviewsP, 0, all, rl, rpl);

        for (int i = 0; i < all.length; ++i) {
            assertEquals(all[i].getRating().get(), rsList.getItem(i).getRating());
            assertEquals(all[i].getSubject().get(), rsList.getItem(i).getSubject());
        }
    }

    @SmallTest
    public void testToGridViewableTrue() {
        addReviews(mController, false, getRandInt());
        Review[] reviews = addReviews(mController, true, getRandInt());
        GvOverviewList oList = (GvOverviewList) mController.toGridViewable(true);
        assertNotNull(oList);
        assertEquals(reviews.length, oList.size());
        for (int i = 0; i < reviews.length; ++i) {
            assertEquals(reviews[i].getRating().get(), oList.getItem(i).getRating());
            assertEquals(reviews[i].getSubject().get(), oList.getItem(i).getSubject());
            assertEquals(reviews[i].getAuthor().getName(), oList.getItem(i).getAuthor());
            assertEquals(reviews[i].getPublishDate(), oList.getItem(i).getPublishDate());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mController = new ControllerReviewCollection<>(new RCollectionReview<Review>());
    }

    private Review[] addReviews(ControllerReviewCollection<Review> controller, boolean published,
            int num) {
        Review[] reviews = new Review[num];
        for (int i = 0; i < num; ++i) {
            Review r = published ? ReviewMocker.newReviewPublished() : ReviewMocker.newReview();
            reviews[i] = r;
            controller.addReview(r);
        }

        return reviews;
    }

    private int getRandInt() {
        Random rand = new Random();
        return MIN + rand.nextInt(MAX - MIN);
    }
}
