/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test;

import android.test.AndroidTestCase;

import com.chdryra.android.reviewer.ControllerReviewCollection;
import com.chdryra.android.reviewer.GVReviewDataList;
import com.chdryra.android.reviewer.GVReviewOverviewList;
import com.chdryra.android.reviewer.GVReviewSubjectRatingList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;

import java.util.Random;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ControllerReviewCollectionTest extends AndroidTestCase {
    private static final int MIN = 10;
    private static final int MAX = 20;
    private ControllerReviewCollection<Review> mController;

    public void testAddReview() {
        GVReviewDataList list = mController.toGridViewable(false);
        GVReviewDataList listP = mController.toGridViewable(true);
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

    public void testToGridViewableFalse() {
        Review[] reviews = addReviews(mController, false, getRandInt());
        GVReviewSubjectRatingList rsList = (GVReviewSubjectRatingList) mController.toGridViewable
                (false);
        assertNotNull(rsList);
        assertEquals(reviews.length, rsList.size());
        for (int i = 0; i < reviews.length; ++i) {
            assertEquals(reviews[i].getRating().get(), rsList.getItem(i).getRating());
            assertEquals(reviews[i].getSubject().get(), rsList.getItem(i).getSubject());
        }
    }

    public void testToGridViewableTrue() {
        Review[] reviews = addReviews(mController, true, getRandInt());
        GVReviewOverviewList oList = (GVReviewOverviewList) mController.toGridViewable(true);
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
            Review r = published ? ReviewMocker.newReviewPublished() : ReviewMocker
                    .newReview();
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
