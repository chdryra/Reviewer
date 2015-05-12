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

import com.chdryra.android.reviewer.Controller.ChildListWrapper;
import com.chdryra.android.reviewer.Controller.ChildrenExpander;
import com.chdryra.android.reviewer.Controller.GridDataExpander;
import com.chdryra.android.reviewer.Controller.GridDataWrapper;
import com.chdryra.android.reviewer.Controller.ReviewNodeAdapter;
import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.reviewer.View.GvData;
import com.chdryra.android.reviewer.View.GvDataList;
import com.chdryra.android.reviewer.View.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeAdapterTest extends AndroidTestCase {
    private static final int NUM = 10;
    private Author                  mAuthor;
    private ReviewNode               mNode;
    private ReviewNodeAdapter mAdapter;
    private ReviewIdableList<Review> mReviews;

    @SmallTest
    public void testGetSubject() {
        assertEquals(mNode.getSubject().get(), mAdapter.getSubject());
    }

    @SmallTest
    public void testGetRating() {
        assertEquals(getRating(), mAdapter.getRating(), 0.0001);
    }

    @SmallTest
    public void testGetAverageRating() {
        assertEquals(getRating(), mAdapter.getAverageRating(), 0.0001);
    }

    @SmallTest
    public void testGetCovers() {
        assertEquals(0, mAdapter.getCovers().size());
    }

    @SmallTest
    public void testGetGridData() {
        GvReviewList oList = (GvReviewList) mAdapter.getGridData();
        assertNotNull(oList);
        assertEquals(mReviews.size(), oList.size());
        for (int i = 0; i < mReviews.size(); ++i) {
            ReviewNode review = (ReviewNode) mReviews.getItem(i);
            assertEquals(review.getRating().get(), oList.getItem(i).getRating());
            assertEquals(review.getSubject().get(), oList.getItem(i).getSubject());
            assertEquals(review.getAuthor(), oList.getItem(i).getAuthor());
            assertEquals(review.getPublishDate(), oList.getItem(i).getPublishDate());
        }
    }

    @SmallTest
    public void testExpandable() {
        GvDataList data = mAdapter.getGridData();
        for (int i = 0; i < data.size(); ++i) {
            GvData datum = (GvData) data.getItem(i);
            assertTrue(mAdapter.isExpandable(datum));
            assertNotNull(mAdapter.expandItem(datum));
        }
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAuthor = new Author(RandomString.nextWord(), UserId.generateId());
        setAdapter();
    }

    private void setAdapter() {
        mReviews = new ReviewIdableList<>();
        for (int i = 0; i < NUM; ++i) {
            mReviews.add(ReviewMocker.newReviewNode(false));
        }
        mNode = FactoryReview.createStaticCollection(mAuthor, new Date(),
                RandomString.nextWord(), mReviews);
        GridDataWrapper wrapper = new ChildListWrapper(mNode);
        GridDataExpander expander = new ChildrenExpander(mContext, mNode);
        mAdapter = new ReviewNodeAdapter(getContext(), mNode, wrapper, expander);
    }

    private float getRating() {
        float rating = 0f;
        for (Review review : mReviews) {
            rating += review.getRating().get() / mReviews.size();
        }

        return rating;
    }
}
