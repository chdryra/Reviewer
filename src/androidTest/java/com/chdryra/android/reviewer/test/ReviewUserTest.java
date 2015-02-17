/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 17 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.MdRating;
import com.chdryra.android.reviewer.MdSubject;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewUser;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 17/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewUserTest extends TestCase {
    private static final int NUM = 5;

    private Author mAuthor;
    private Date   mDate;
    private String mSubject;
    private float  mRating;

    private GvCommentList  mComments;
    private GvImageList    mImages;
    private GvFactList     mFacts;
    private GvLocationList mLocations;
    private GvUrlList      mUrls;

    private Review mReview;

    @SmallTest
    public void testGetId() {
        assertNotNull(mReview.getId());
    }

    @SmallTest
    public void testGetSubject() {
        MdSubject subject = mReview.getSubject();
        assertNotNull(subject);
        assertEquals(mReview, subject.getHoldingReview());
        assertTrue(subject.hasData());
        assertEquals(mSubject, mReview.getSubject().get());
    }

    @SmallTest
    public void testGetRating() {
        MdRating rating = mReview.getRating();
        assertNotNull(rating);
        assertEquals(mReview, rating.getHoldingReview());
        assertTrue(rating.hasData());
        assertEquals(mRating, mReview.getRating().get());
    }

    @SmallTest
    public void testGetReviewNode() {
        ReviewNode node = mReview.getReviewNode();
        assertEquals(mReview, node.getReview());
        assertEquals(0, node.getChildren().size());
        assertNull(node.getParent());
        assertEquals(mReview.getId(), node.getId());
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
    public void testHasComments() {
        assertTrue(mReview.hasComments());
        Review noComments = new ReviewUser(mAuthor, mDate, mSubject, mRating,
                new GvCommentList(), mImages, mFacts, mLocations, mUrls);
        assertFalse(noComments.hasComments());
    }

    @SmallTest
    public void testGetFacts() {
        MdGvEquality.check(mReview.getFacts(), mFacts);
    }

    @SmallTest
    public void testHasFacts() {
        assertTrue(mReview.hasFacts());
        Review noFacts = new ReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, mImages, new GvFactList(), mLocations, mUrls);
        assertFalse(noFacts.hasFacts());
    }

    @SmallTest
    public void testGetImages() {
        MdGvEquality.check(mReview.getImages(), mImages);
    }

    @SmallTest
    public void testHasImages() {
        assertTrue(mReview.hasImages());
        Review noImages = new ReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, new GvImageList(), mFacts, mLocations, mUrls);
        assertFalse(noImages.hasImages());
    }

    @SmallTest
    public void testGetUrls() {
        MdGvEquality.check(mReview.getUrls(), mUrls);
    }

    @SmallTest
    public void testHasUrls() {
        assertTrue(mReview.hasUrls());
        Review noUrls = new ReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, mImages, mFacts, mLocations, new GvUrlList());
        assertFalse(noUrls.hasUrls());
    }

    @SmallTest
    public void testGetLocations() {
        MdGvEquality.check(mReview.getLocations(), mLocations);
    }

    @SmallTest
    public void testHasLocations() {
        assertTrue(mReview.hasLocations());
        Review noLocations = new ReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, mImages, mFacts, new GvLocationList(), mUrls);
        assertFalse(noLocations.hasLocations());
    }

    @Override
    protected void setUp() throws Exception {
        mAuthor = new Author(RandomString.nextWord());
        mDate = RandomDate.nextDate();
        mSubject = RandomString.nextWord();
        mRating = RandomRating.nextRating();

        mComments = GvDataMocker.newCommentList(NUM);
        mImages = GvDataMocker.newImageList(NUM);
        mFacts = GvDataMocker.newFactList(NUM);
        mLocations = GvDataMocker.newLocationList(NUM);
        mUrls = GvDataMocker.newUrlList(NUM);

        mReview = new ReviewUser(mAuthor, mDate, mSubject, mRating, mComments, mImages, mFacts,
                mLocations, mUrls);
    }
}
