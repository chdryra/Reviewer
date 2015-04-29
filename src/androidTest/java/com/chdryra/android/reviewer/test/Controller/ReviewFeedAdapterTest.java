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
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
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
    private ReviewIdableList<ReviewNode> mReviews;
    
    @SmallTest
    public void testGetImages() {
        assertNull(mAdapter.getCovers());
    }

    @SmallTest
    public void testGetAverageRating() {
        float rating = 0f;
        for (ReviewNode review : mReviews) {
            rating += review.getRating().get() / mReviews.size();
        }

        assertEquals(rating, mAdapter.getAverageRating(), 0.0001);
    }
    
    @SmallTest
    public void testGetGridData() {
        GvReviewList oList = (GvReviewList) mAdapter.getGridData();
        assertNotNull(oList);
        assertEquals(mReviews.size(), oList.size());
        for (int i = 0; i < mReviews.size(); ++i) {
            ReviewNode review = mReviews.getItem(i);
            assertEquals(review.getRating().get(), oList.getItem(i).getRating());
            assertEquals(review.getSubject().get(), oList.getItem(i).getSubject());
            assertEquals(review.getAuthor(), oList.getItem(i).getAuthor());
            assertEquals(review.getPublishDate(), oList.getItem(i).getPublishDate());
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAuthor = new Author(RandomString.nextWord(), UserId.generateId());
        mDate = RandomDate.nextDate();
        setAdapter();
    }

    private void setAdapter() {
        mReviews = new ReviewIdableList<>();
        for (int i = 0; i < NUM; ++i) {
            mReviews.add(ReviewMocker.newReviewNode());
        }
        mAdapter = new ReviewFeedAdapter(getContext(), mAuthor.getName(), mReviews);
    }
}
