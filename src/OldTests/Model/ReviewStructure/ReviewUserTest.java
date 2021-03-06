/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.startouch.test.Model.ReviewStructure;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumAuthor;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.PublishDate;
import com.chdryra.android.startouch.Model.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterion;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdCriterionList;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.MdIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewUser;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.UserModel.AuthorId;
import com.chdryra.android.startouch.Presenter.ReviewBuilding.Interfaces.ReviewStamp;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvCommentList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvFactList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvImageList;
import com.chdryra.android.startouch.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvLocationList;
import com.chdryra.android.startouch.test.TestUtils.GvDataMocker;
import com.chdryra.android.startouch.test.TestUtils.MdGvEquality;
import com.chdryra.android.startouch.test.TestUtils.RandomPublishDate;
import com.chdryra.android.startouch.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserTest extends TestCase {
    private static final int NUM = 5;

    private DatumAuthor mAuthor;
    private PublishDate mDate;
    private String mSubject;
    private float mRating;

    private GvCommentList mComments;
    private GvImageList mImages;
    private GvFactList mFacts;
    private GvLocationList mLocations;

    private ReviewStamp mPublisher;
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
            MdCriterion criterion = criteria.getItem(i);
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

    //Overridden
    @Override
    protected void setUp() throws Exception {
        mAuthor = new DatumAuthor(RandomString.nextWord(), AuthorId.generateId());
        mDate = RandomPublishDate.nextDate();
        mSubject = RandomString.nextWord();
        mRating = RandomRating.nextRating();

        mComments = GvDataMocker.newCommentList(NUM, false);
        mImages = GvDataMocker.newImageList(NUM, false);
        mFacts = GvDataMocker.newFactList(NUM, false);
        mLocations = GvDataMocker.newLocationList(NUM, false);

        mPublisher = new ReviewStamp(mAuthor, mDate);

        mCriteria = new MdIdableCollection<>();
        for (int i = 0; i < NUM; ++i) {
            mCriteria.add(newCriterion(mPublisher));
        }

        mReview = new ReviewUser(MdReviewId.newId(mPublisher), mPublisher.getAuthor(),
                mPublisher.getDate(), mSubject, mRating, mComments, mImages, mFacts,
                mLocations, mCriteria, false);
    }

    private Review newCriterion(ReviewStamp publisher) {
        return FactoryReviews.createUserReview(RandomString.nextWord(), RandomRating
                .nextRating());
    }
}
