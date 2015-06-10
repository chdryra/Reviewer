/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test.Model;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Model.Author;
import com.chdryra.android.reviewer.Model.FactoryReview;
import com.chdryra.android.reviewer.Model.PublishDate;
import com.chdryra.android.reviewer.Model.Review;
import com.chdryra.android.reviewer.Model.ReviewNode;
import com.chdryra.android.reviewer.Model.UserId;
import com.chdryra.android.reviewer.View.GvCommentList;
import com.chdryra.android.reviewer.View.GvFactList;
import com.chdryra.android.reviewer.View.GvImageList;
import com.chdryra.android.reviewer.View.GvLocationList;
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
        ReviewNode node = FactoryReview.createReviewNode(mReview);

        assertEquals(mSubject, node.getSubject().get());
        assertEquals(mRating, node.getRating().get());
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
}
