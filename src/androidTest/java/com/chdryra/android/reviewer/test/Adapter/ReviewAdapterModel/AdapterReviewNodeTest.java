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

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.MdGvConverter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.AdapterReviewNode;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ViewerChildList;
import com.chdryra.android.reviewer.Model.ReviewData.IdableList;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewPublisher;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.TagsModel.TagsManager;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.ReviewsProviderModel.ReviewsRepository;
import com.chdryra.android.reviewer.View.GvDataModel.GvReviewOverviewList;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewsRepository;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewNodeTest extends AndroidTestCase {
    private static final int NUM = 10;
    private Author mAuthor;
    private ReviewNode mNode;
    private AdapterReviewNode<GvReviewOverviewList.GvReviewOverview> mAdapter;
    private IdableList<ReviewNode> mReviews;

    @SmallTest
    public void testGetSubject() {
        assertEquals(mNode.getSubject().get(), mAdapter.getSubject());
    }

    @SmallTest
    public void testGetRating() {
        assertEquals(getRating(), mAdapter.getRating(), 0.0001);
    }

    @SmallTest
    public void testGetCovers() {
        assertEquals(mNode.getImages().getCovers().size(), mAdapter.getCovers().size());
    }

    @SmallTest
    public void testGetGridData() {
        GvReviewOverviewList oList = (GvReviewOverviewList) mAdapter.getGridData();
        assertNotNull(oList);
        assertEquals(mReviews.size(), oList.size());
        for (int i = 0; i < mReviews.size(); ++i) {
            ReviewNode review = mReviews.getItem(i);
            assertEquals(review.getRating().getValue(), oList.getItem(i).getRating());
            assertEquals(review.getSubject().get(), oList.getItem(i).getSubject());
            assertEquals(review.getAuthor(), oList.getItem(i).getAuthor());
            assertEquals(review.getPublishDate().getDate(), oList.getItem(i).getPublishDate());
        }
    }

    @SmallTest
    public void testExpandable() {
        GvReviewOverviewList data = (GvReviewOverviewList) mAdapter.getGridData();
        for (int i = 0; i < data.size(); ++i) {
            GvReviewOverviewList.GvReviewOverview datum = data.getItem(i);
            assertTrue(mAdapter.isExpandable(datum));
            assertNotNull(mAdapter.expandGridCell(datum));
        }
    }

    //private methods
    private float getRating() {
        float rating = 0f;
        for (Review review : mReviews) {
            rating += review.getRating().getValue() / mReviews.size();
        }

        return rating;
    }

    private void setAdapter() {
        ReviewPublisher publisher = new ReviewPublisher(mAuthor, PublishDate.now());
        FactoryReview reviewFactory = new FactoryReview(new MdGvConverter());
        Review review = reviewFactory.createReviewUser(publisher, RandomString.nextWord(), 0f);
        ReviewTreeNode collection = reviewFactory.createReviewTreeNode(review, true);
        mReviews = new IdableList<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewTreeNode child = (ReviewTreeNode) ReviewMocker.newReviewNode(false);
            mReviews.add(child);
            collection.addChild(child);
        }

        mNode = collection;
        RandomReviewsRepository rando = new RandomReviewsRepository()
        ReviewsRepository repo = RandomReviewsRepository.nextRepository(mNode);
        ViewerChildList wrapper = new ViewerChildList(getContext(), mNode, repo);
        mAdapter = new AdapterReviewNode<>(mNode, wrapper);
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAuthor = new Author(RandomString.nextWord(), UserId.generateId());
        setAdapter();
    }
}
