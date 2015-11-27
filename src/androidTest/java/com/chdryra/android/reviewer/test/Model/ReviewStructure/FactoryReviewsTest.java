/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewStructure;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.Author;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.UserId;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvFactList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomPublishDate;
import com.chdryra.android.reviewer.test.TestUtils.RandomPublisher;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsTest extends TestCase {
    private static final int NUM = 3;
    private Review mReview;
    private Author mAuthor;
    private PublishDate mDate;
    private String mSubject;
    private float mRating;
    private GvCommentList mComments;
    private GvImageList mImages;
    private GvFactList mFacts;
    private GvLocationList mLocations;
    private MdIdableCollection<Review> mCriteria;

    @SmallTest
    public void testCreateReviewUser() {
        assertEquals(mSubject, mReview.getSubject().getSubject());
        assertEquals(mRating, mReview.getRating().getRating());
        assertEquals(mAuthor, mReview.getAuthor());
        assertEquals(mDate, mReview.getPublishDate());
        MdGvEquality.check(mReview.getComments(), mComments);
        MdGvEquality.check(mReview.getImages(), mImages);
        MdGvEquality.check(mReview.getFacts(), mFacts);
        MdGvEquality.check(mReview.getLocations(), mLocations);

        assertFalse(mReview.isRatingAverageOfCriteria());
        MdCriterionList criteria = mReview.getCriteria();
        assertEquals(mCriteria.size(), criteria.size());
        for (int i = 0; i < NUM; ++i) {
            assertEquals(mCriteria.getItem(i), criteria.getItem(i));
        }
    }

    @SmallTest
    public void testCreateReviewTreeNode() {
        ReviewTreeNode node = FactoryReviews.createReviewNodeComponent(mReview, true);
        checkNode(node);
        assertTrue(node.isRatingAverageOfChildren());
        node = FactoryReviews.createReviewNodeComponent(mReview, false);
        checkNode(node);
        assertFalse(node.isRatingAverageOfChildren());
    }

    @SmallTest
    public void testCreateMetaReview() {
        MdIdableCollection<Review> reviews = new MdIdableCollection<>();
        float averageRating = 0f;
        for (int i = 0; i < NUM; ++i) {
            Review review = ReviewMocker.newReview();
            reviews.add(review);
            averageRating += review.getRating().getRating() / NUM;
        }
        String subject = RandomString.nextWord();

        ReviewPublisher publisher = RandomPublisher.nextPublisher();
        ReviewNode meta = FactoryReviews.createMetaReview(reviews, subject);

        assertEquals(publisher.getAuthor(), meta.getAuthor());
        assertEquals(publisher.getDate(), meta.getPublishDate());
        assertEquals(subject, meta.getSubject().getSubject());
        assertEquals(averageRating, meta.getRating().getRating());
        MdIdableCollection<ReviewNode> children = meta.getChildren();
        assertEquals(NUM, children.size());
        for (int i = 0; i < NUM; ++i) {
            assertEquals(reviews.getItem(i), children.getItem(i).getReview());
        }
    }

    private Review nextReview() {
        Author author = new Author(RandomString.nextWord(), UserId.generateId());
        PublishDate date = RandomPublishDate.nextDate();
        String subject = RandomString.nextWord();
        float rating = RandomRating.nextRating();
        GvCommentList comments = GvDataMocker.newCommentList(NUM, false);
        GvImageList images = GvDataMocker.newImageList(NUM, false);
        GvFactList facts = GvDataMocker.newFactList(NUM, false);
        GvLocationList locations = GvDataMocker.newLocationList(NUM, false);

        ReviewPublisher publisher = new ReviewPublisher(author, date);
        return FactoryReviews.createUserReview(subject, rating,
                comments, images, facts, locations, new MdIdableCollection<Review>(), false);
    }

    private void checkNode(ReviewNode node) {
        assertEquals(mSubject, node.getSubject().getSubject());
        assertEquals(node.isRatingAverageOfChildren() ? 0f : mRating, node.getRating().getRating());
        assertEquals(mAuthor, node.getAuthor());
        assertEquals(mDate, node.getPublishDate());
        MdGvEquality.check(node.getComments(), mComments);
        MdGvEquality.check(node.getImages(), mImages);
        MdGvEquality.check(node.getFacts(), mFacts);
        MdGvEquality.check(node.getLocations(), mLocations);

        assertEquals(mReview, node.getReview());
        assertEquals(mReview.getMdReviewId(), node.getMdReviewId());
        assertNull(node.getParent());
        assertEquals(0, node.getChildren().size());
    }

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = new Author(RandomString.nextWord(), UserId.generateId());
        mDate = RandomPublishDate.nextDate();
        mSubject = RandomString.nextWord();
        mRating = RandomRating.nextRating();
        mComments = GvDataMocker.newCommentList(NUM, false);
        mImages = GvDataMocker.newImageList(NUM, false);
        mFacts = GvDataMocker.newFactList(NUM, false);
        mLocations = GvDataMocker.newLocationList(NUM, false);
        mCriteria = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mCriteria.add(nextReview());
        }
        ReviewPublisher publisher = new ReviewPublisher(mAuthor, mDate);
        mReview = FactoryReviews.createUserReview(mSubject, mRating,
                mComments, mImages, mFacts, mLocations, mCriteria, false);
    }
}
