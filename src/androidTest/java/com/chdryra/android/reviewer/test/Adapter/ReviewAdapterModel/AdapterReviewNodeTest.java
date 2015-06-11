/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.reviewer.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ExpanderChildren;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.GridDataExpander;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerChildList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewIdableList;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewList;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewNodeTest extends AndroidTestCase {
    private static final int NUM = 10;
    private Author                  mAuthor;
    private ReviewNode               mNode;
    private AdapterReviewNode        mAdapter;
    private ReviewIdableList<ReviewNode> mReviews;

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
        assertEquals(mNode.getImages().getCovers().size(), mAdapter.getCovers().size());
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

        Review review = FactoryReview.createReviewUser(mAuthor, PublishDate.now(), RandomString
                .nextWord
                        (), 0f);
        ReviewTreeNode collection = FactoryReview.createReviewTreeNode(review, true);
        mReviews = new ReviewIdableList<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewTreeNode child = (ReviewTreeNode) ReviewMocker.newReviewNode(false);
            mReviews.add(child);
            collection.addChild(child);
        }

        mNode = collection;
        ViewerChildList wrapper = new ViewerChildList(mNode);
        GridDataExpander expander = new ExpanderChildren(mContext, mNode);
        mAdapter = new AdapterReviewNode(mNode, wrapper, expander);
    }

    private float getRating() {
        float rating = 0f;
        for (Review review : mReviews) {
            rating += review.getRating().get() / mReviews.size();
        }

        return rating;
    }
}
