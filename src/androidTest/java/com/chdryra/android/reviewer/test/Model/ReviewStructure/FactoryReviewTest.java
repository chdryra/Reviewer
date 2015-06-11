/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test.Model.ReviewStructure;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewStructure.FactoryReview;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewStructure.ReviewTreeNode;
import com.chdryra.android.reviewer.Model.UserData.Author;
import com.chdryra.android.reviewer.Model.UserData.UserId;
import com.chdryra.android.reviewer.View.GvDataModel.GvCommentList;
import com.chdryra.android.reviewer.View.GvDataModel.GvFactList;
import com.chdryra.android.reviewer.View.GvDataModel.GvImageList;
import com.chdryra.android.reviewer.View.GvDataModel.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomPublishDate;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTest extends TestCase {
    private static final int NUM = 3;
    private Review         mReview;
    private Author         mAuthor;
    private PublishDate mDate;
    private String         mSubject;
    private float          mRating;
    private GvCommentList  mComments;
    private GvImageList    mImages;
    private GvFactList     mFacts;
    private GvLocationList mLocations;

    @SmallTest
    public void testCreateReviewUser() {
        assertEquals(mSubject, mReview.getSubject().get());
        assertEquals(mRating, mReview.getRating().get());
        assertEquals(mAuthor, mReview.getAuthor());
        assertEquals(mDate, mReview.getPublishDate());
        MdGvEquality.check(mReview.getComments(), mComments);
        MdGvEquality.check(mReview.getImages(), mImages);
        MdGvEquality.check(mReview.getFacts(), mFacts);
        MdGvEquality.check(mReview.getLocations(), mLocations);
    }

    @SmallTest
    public void testCreateReviewNode() {
        checkNode(FactoryReview.createReviewNode(mReview));
    }

    @SmallTest
    public void testCreateReviewTreeNode() {
        ReviewTreeNode node = FactoryReview.createReviewTreeNode(mReview, true);
        checkNode(node);
        assertTrue(node.isRatingAverageOfChildren());
        node = FactoryReview.createReviewTreeNode(mReview, false);
        checkNode(node);
        assertFalse(node.isRatingAverageOfChildren());
    }

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

        mReview = FactoryReview.createReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, mImages, mFacts, mLocations);
    }

    private void checkNode(ReviewNode node) {
        assertEquals(mSubject, node.getSubject().get());
        assertEquals(node.isRatingAverageOfChildren() ? 0f : mRating, node.getRating().get());
        assertEquals(mAuthor, node.getAuthor());
        assertEquals(mDate, node.getPublishDate());
        MdGvEquality.check(node.getComments(), mComments);
        MdGvEquality.check(node.getImages(), mImages);
        MdGvEquality.check(node.getFacts(), mFacts);
        MdGvEquality.check(node.getLocations(), mLocations);

        assertEquals(mReview, node.getReview());
        assertEquals(mReview.getId(), node.getId());
        assertNull(node.getParent());
        assertEquals(0, node.getChildren().size());
    }
}
