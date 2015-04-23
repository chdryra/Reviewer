/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test.Controller;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Controller.ReviewFeedAdapter;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewFeedAdapterTest extends AndroidTestCase {
    private static final int NUM = 10;
    private Author                  mAuthor;
    private Date                    mDate;
    private ReviewFeedAdapter mAdapter;

    @SmallTest
    public void testGetImages() {
        assertNull(mAdapter.getCovers());
    }

    @SmallTest
    public void testGetAverageRating() {
        assertEquals(0f, mAdapter.getAverageRating());

        Review[] reviews = addReviews(mAdapter);
        float rating = 0f;
        for (Review review : reviews) {
            rating += review.getRating().get() / reviews.length;
        }

        assertEquals(rating, mAdapter.getAverageRating(), 0.0001);
    }

    @SmallTest
    public void testAddReview() {
        GvDataList list = mAdapter.getGridData();
        assertNotNull(list);
        assertEquals(0, list.size());

        Review[] reviews = addReviews(mAdapter);

        list = mAdapter.getGridData();
        assertNotNull(list);
        assertEquals(reviews.length, list.size());
    }

    @SmallTest
    public void testGetGridData() {
        Review[] reviews = addReviews(mAdapter);
        GvReviewList oList = (GvReviewList) mAdapter.getGridData();
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
        mAuthor = new Author(RandomString.nextWord(), UserId.generateId());
        mDate = RandomDate.nextDate();
        mAdapter = new ReviewFeedAdapter(getContext(), mAuthor.getName());
    }

    private Review[] addReviews(ReviewFeedAdapter adapter) {
        Review[] reviews = new Review[NUM];
        for (int i = 0; i < NUM; ++i) {
            ReviewNode r = ReviewMocker.newReviewNode();
            reviews[i] = r;
            adapter.add(r);
        }

        return reviews;
    }
}
