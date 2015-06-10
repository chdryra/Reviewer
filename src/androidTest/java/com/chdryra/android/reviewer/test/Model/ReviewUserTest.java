/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.MdRating;
import com.chdryra.android.reviewer.Model.MdSubject;
import com.chdryra.android.reviewer.Model.PublishDate;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewUser;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomPublishDate;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.RandomReviewId;
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
    private String      mSubject;
    private float       mRating;

    private GvCommentList  mComments;
    private GvImageList    mImages;
    private GvFactList     mFacts;
    private GvLocationList mLocations;

    private Review mReview;

    @SmallTest
    public void testGetId() {
        assertNotNull(mReview.getId());
    }

    @SmallTest
    public void testGetSubject() {
        MdSubject subject = mReview.getSubject();
        assertNotNull(subject);
        assertEquals(mReview.getId(), subject.getReviewId());
        assertTrue(subject.hasData());
        assertEquals(mSubject, mReview.getSubject().get());
    }

    @SmallTest
    public void testGetRating() {
        MdRating rating = mReview.getRating();
        assertNotNull(rating);
        assertEquals(mReview.getId(), rating.getReviewId());
        assertTrue(rating.hasData());
        assertEquals(mRating, mReview.getRating().get());
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

        mReview = new ReviewUser(RandomReviewId.nextId(), mAuthor, mDate, mSubject, mRating,
                mComments, mImages, mFacts, mLocations);
    }
}
