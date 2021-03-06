/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 December, 2014
 */

package com.chdryra.android.startouch.test.Adapter.ReviewAdapterModel;

import android.test.AndroidTestCase;
import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.startouch.Model.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTreeMutable;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.UserModel.AuthorId;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsFeed;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewStamp;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverview;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvReviewOverviewList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View
        .AdapterReviewNode;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.View.ViewerChildList;
import com.chdryra.android.startouch.test.TestUtils.RandomReviewsRepository;
import com.chdryra.android.startouch.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

/**
 * Created by: Rizwan Choudrey
 * On: 08/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class AdapterReviewNodeTest extends AndroidTestCase {
    private static final int NUM = 10;
    private DatumAuthor mAuthor;
    private ReviewNode mNode;
    private AdapterReviewNode<GvReviewOverview> mAdapter;
    private MdIdableCollection<ReviewNode> mReviews;

    @SmallTest
    public void testGetSubject() {
        assertEquals(mNode.getSubject().getSubject(), mAdapter.getSubject());
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
            ReviewNode review = mReviews.get(i);
            assertEquals(review.getRating().getRating(), oList.get(i).getRating());
            assertEquals(review.getSubject().getSubject(), oList.get(i).getSubject());
            assertEquals(review.getAuthor(), oList.get(i).getAuthor());
            assertEquals(review.getPublishDate().getDate(), oList.get(i).getPublishDate());
        }
    }

    @SmallTest
    public void testExpandable() {
        GvReviewOverviewList data = (GvReviewOverviewList) mAdapter.getGridData();
        for (int i = 0; i < data.size(); ++i) {
            GvReviewOverview datum = data.getItem(i);
            assertTrue(mAdapter.isExpandable(datum));
            assertNotNull(mAdapter.expandGridCell(datum));
        }
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mAuthor = new DatumAuthor(RandomString.nextWord(), AuthorId.generateId());
        setAdapter();
    }

    //private methods
    private float getRating() {
        float rating = 0f;
        for (Review review : mReviews) {
            rating += review.getRating().getRating() / mReviews.size();
        }

        return rating;
    }

    private void setAdapter() {
        ReviewStamp publisher = new ReviewStamp(mAuthor, PublishDate.now());
        FactoryReviews reviewFactory = new FactoryReviews(new MdGvConverter());
        Review review = reviewFactory.createUserReview(RandomString.nextWord(), 0f);
        ReviewTreeMutable collection = reviewFactory.createReviewNodeComponent(review, true);
        mReviews = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            ReviewTreeMutable child = (ReviewTreeMutable) ReviewMocker.newReviewNode(false);
            mReviews.add(child);
            collection.addChild(child);
        }

        mNode = collection;
        RandomReviewsRepository rando = new RandomReviewsRepository();
        ReviewsFeed repo = RandomReviewsRepository.nextRepository(mNode);
        ViewerChildList wrapper = new ViewerChildList(getContext(), mNode, repo);
        mAdapter = new AdapterReviewNode<>(mNode, wrapper);
    }
}
