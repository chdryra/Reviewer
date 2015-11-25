/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewStructure;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdRating;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdSubject;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.PublishDate;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewPublisher;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.reviewer.Model.Interfaces.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.ReviewStructure.ReviewUser;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.Author;
import com.chdryra.android.reviewer.Model.Implementation.UserModel.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.Implementation.Data.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomPublishDate;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserTest extends TestCase {
    private static final int NUM = 5;

    private Author mAuthor;
    private PublishDate mDate;
    private String mSubject;
    private float mRating;

    private GvCommentList mComments;
    private GvImageList mImages;
    private GvFactList mFacts;
    private GvLocationList mLocations;

    private ReviewPublisher mPublisher;
    private Review mReview;
    private MdIdableCollection<Review> mCriteria;

    @SmallTest
    public void testGetId() {
        assertNotNull(mReview.getMdReviewId());
    }

    @SmallTest
    public void testGetSubject() {
        MdSubject subject = mReview.getSubject();
        assertNotNull(subject);
        assertEquals(mReview.getMdReviewId(), subject.getReviewId());
        assertTrue(subject.hasData());
        assertEquals(mSubject, mReview.getSubject().getSubject());
    }

    @SmallTest
    public void testGetRating() {
        MdRating rating = mReview.getRating();
        assertNotNull(rating);
        assertEquals(mReview.getMdReviewId(), rating.getReviewId());
        assertTrue(rating.hasData());
        assertEquals(mRating, mReview.getRating().getRating());
    }

    @SmallTest
    public void testGetAuthor() {
        assertEquals(mAuthor, mReview.getAuthor());
    }

    @SmallTest
    public void testGetPublishDate() {
        assertEquals(mDate, mReview.getPublishDate());
    }

    @SmallTest
    public void testGetComments() {
        MdGvEquality.check(mReview.getComments(), mComments);
    }

    @SmallTest
    public void testGetFacts() {
        MdGvEquality.check(mReview.getFacts(), mFacts);
    }

    @SmallTest
    public void testGetImages() {
        MdGvEquality.check(mReview.getImages(), mImages);
    }

    @SmallTest
    public void testGetLocations() {
        MdGvEquality.check(mReview.getLocations(), mLocations);
    }

    @SmallTest
    public void testGetCriteria() {
        MdCriterionList criteria = mReview.getCriteria();
        assertEquals(mReview.getMdReviewId(), criteria.getReviewId());
        assertEquals(mCriteria.size(), criteria.size());
        for (int i = 0; i < criteria.size(); ++i) {
            MdCriterionList.MdCriterion criterion = criteria.getItem(i);
            Review criterionReview = mCriteria.getItem(i);
            assertEquals(mReview.getMdReviewId(), criterion.getReviewId());
            assertEquals(criterionReview, criterion.getReview());
        }
    }

    @SmallTest
    public void testGetTreeRepresentation() {
        ReviewNode node = mReview.getTreeRepresentation();
        assertEquals(mReview, node.getReview());
        assertEquals(0, node.getChildren().size());
        assertFalse(node.isRatingAverageOfChildren());
    }

    private Review newCriterion(ReviewPublisher publisher) {
        return FactoryReviews.createUserReview(RandomString.nextWord(), RandomRating
                .nextRating());
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

        mPublisher = new ReviewPublisher(mAuthor, mDate);

        mCriteria = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mCriteria.add(newCriterion(mPublisher));
        }

        mReview = new ReviewUser(MdReviewId.newId(mPublisher), mPublisher.getAuthor(),
                mPublisher.getDate(), mSubject, mRating, mComments, mImages, mFacts,
                mLocations, mCriteria, false);
    }
}
