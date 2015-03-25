/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 18 February, 2015
 */

package com.chdryra.android.reviewer.test;

import android.test.suitebuilder.annotation.SmallTest;

import com.chdryra.android.reviewer.Author;
import com.chdryra.android.reviewer.FactoryReview;
import com.chdryra.android.reviewer.GvCommentList;
import com.chdryra.android.reviewer.GvFactList;
import com.chdryra.android.reviewer.GvImageList;
import com.chdryra.android.reviewer.GvLocationList;
import com.chdryra.android.reviewer.GvUrlList;
import com.chdryra.android.reviewer.RCollectionReview;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.test.TestUtils.GvDataMocker;
import com.chdryra.android.reviewer.test.TestUtils.MdGvEquality;
import com.chdryra.android.reviewer.test.TestUtils.RandomRating;
import com.chdryra.android.reviewer.test.TestUtils.ReviewMocker;
import com.chdryra.android.testutils.RandomDate;
import com.chdryra.android.testutils.RandomString;

import junit.framework.TestCase;

import java.util.Date;

/**
 * Created by: Rizwan Choudrey
 * On: 18/02/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTest extends TestCase {
    private static final int NUM = 3;
    private Review         mReview;
    private Author         mAuthor;
    private Date           mDate;
    private String         mSubject;
    private float          mRating;
    private GvCommentList  mComments;
    private GvImageList    mImages;
    private GvFactList     mFacts;
    private GvLocationList mLocations;
    private GvUrlList      mUrls;

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

    @SmallTest
    public void testCreateReviewTree() {
        RCollectionReview<Review> children = new RCollectionReview<>();
        for (int i = 0; i < NUM; ++i) {
            children.add(ReviewMocker.newReview());
        }

        ReviewNode tree = FactoryReview.createReviewTree(mReview, children, false);

        assertEquals(mSubject, tree.getSubject().get());
        assertEquals(mRating, tree.getRating().get());
        assertEquals(mAuthor, tree.getAuthor());
        assertEquals(mDate, tree.getPublishDate());
        MdGvEquality.check(tree.getComments(), mComments);
        MdGvEquality.check(tree.getImages(), mImages);
        MdGvEquality.check(tree.getFacts(), mFacts);
        MdGvEquality.check(tree.getLocations(), mLocations);

        assertEquals(mReview, tree.getReview());
        assertEquals(mReview.getId(), tree.getId());
        assertNull(tree.getParent());

        RCollectionReview<ReviewNode> childNodes = tree.getChildren();
        assertEquals(children.size(), childNodes.size());
        for (int i = 0; i < children.size(); ++i) {
            Review childReview = children.getItem(i);
            ReviewNode childNode = childNodes.getItem(i);
            assertEquals(childReview, childNode.getReview());
            assertEquals(childReview.getSubject(), childNode.getSubject());
            assertEquals(childReview.getRating(), childNode.getRating());
            assertEquals(childReview.getAuthor(), childNode.getAuthor());
            assertEquals(childReview.getPublishDate(), childNode.getPublishDate());
            assertEquals(tree, childNode.getParent());
            assertEquals(0, childNode.getChildren().size());
        }
    }

    @Override
    protected void setUp() throws Exception {
        mAuthor = new Author(RandomString.nextWord());
        mDate = RandomDate.nextDate();
        mSubject = RandomString.nextWord();
        mRating = RandomRating.nextRating();
        mComments = GvDataMocker.newCommentList(NUM, false);
        mImages = GvDataMocker.newImageList(NUM, false);
        mFacts = GvDataMocker.newFactList(NUM, false);
        mLocations = GvDataMocker.newLocationList(NUM, false);
        mUrls = GvDataMocker.newUrlList(NUM, false);

        mReview = FactoryReview.createReviewUser(mAuthor, mDate, mSubject, mRating,
                mComments, mImages, mFacts, mLocations);
    }
}
