/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 4 December, 2014
 */

package com.chdryra.android.reviewer.test.TestUtils;

import com.chdryra.android.reviewer.FactoryReview;
import com.chdryra.android.reviewer.PublisherReviewTree;
import com.chdryra.android.reviewer.RDCommentList;
import com.chdryra.android.reviewer.RDFactList;
import com.chdryra.android.reviewer.RDId;
import com.chdryra.android.reviewer.RDImageList;
import com.chdryra.android.reviewer.RDLocationList;
import com.chdryra.android.reviewer.RDRating;
import com.chdryra.android.reviewer.RDSubject;
import com.chdryra.android.reviewer.RDUrlList;
import com.chdryra.android.reviewer.Review;
import com.chdryra.android.reviewer.ReviewEditable;
import com.chdryra.android.reviewer.ReviewNode;
import com.chdryra.android.reviewer.ReviewNodeExpandable;
import com.chdryra.android.reviewer.ReviewTreeEditable;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewMocker {
    private ReviewMocker mReviewMocker;

    private ReviewMocker() {
    }

    public static Review newReview() {
        return getNew();
    }

    public static ReviewEditable newReviewEditable() {
        return getNew();
    }

    public static ReviewNode newReviewNode() {
        return getNew();
    }

    public static ReviewNodeExpandable newReviewnodeExpandable() {
        return getNew();
    }

    private static MockReview getNew() {
        return new MockReview();
    }

    private ReviewMocker get() {
        if (mReviewMocker == null) mReviewMocker = new ReviewMocker();
        return mReviewMocker;
    }

    static class MockReview extends ReviewTreeEditable {
        private MockReview() {
            super(new MockReviewEditable());
        }
    }

    static class MockReviewEditable extends ReviewEditable {
        private RDId           mId;
        private RDSubject      mSubject;
        private RDRating       mRating;
        private ReviewNode     mNode;
        private RDCommentList  mComments;
        private RDFactList     mFacts;
        private RDImageList    mImages;
        private RDUrlList      mUrls;
        private RDLocationList mLocations;

        private MockReviewEditable() {
            mId = RDId.generateId();
            mSubject = new RDSubject("MockReviewEditable", this);
            mRating = new RDRating(3f, this);
            mNode = FactoryReview.createReviewNodeExpandable(this);
            mComments = new RDCommentList();
            mFacts = new RDFactList();
            mImages = new RDImageList();
            mUrls = new RDUrlList();
            mLocations = new RDLocationList();
        }

        @Override
        public RDId getId() {
            return mId;
        }

        @Override
        public RDSubject getSubject() {
            return mSubject;
        }

        @Override
        public RDRating getRating() {
            return mRating;
        }

        @Override
        public ReviewNode getReviewNode() {
            return mNode;
        }

        @Override
        public Review publish(PublisherReviewTree publisher) {
            return publisher.publish(mNode);
        }

        @Override
        public RDCommentList getComments() {
            return mComments;
        }

        @Override
        public boolean hasComments() {
            return mComments.size() > 0;
        }

        @Override
        public RDFactList getFacts() {
            return mFacts;
        }

        @Override
        public boolean hasFacts() {
            return mFacts.size() > 0;
        }

        @Override
        public RDImageList getImages() {
            return mImages;
        }

        @Override
        public boolean hasImages() {
            return mImages.size() > 0;
        }

        @Override
        public RDUrlList getURLs() {
            return mUrls;
        }

        @Override
        public boolean hasUrls() {
            return mUrls.size() > 0;
        }

        @Override
        public RDLocationList getLocations() {
            return mLocations;
        }

        @Override
        public boolean hasLocations() {
            return mLocations.size() > 0;
        }

        @Override
        public void setLocations(RDLocationList locations) {
            mLocations = locations;
            mLocations.setHoldingReview(this);
        }

        @Override
        public void setImages(RDImageList images) {
            mImages = images;
            mImages.setHoldingReview(this);
        }

        @Override
        public void setUrls(RDUrlList urls) {
            mUrls = urls;
            mUrls.setHoldingReview(this);
        }

        @Override
        public void setFacts(RDFactList facts) {
            mFacts = facts;
            mFacts.setHoldingReview(this);
        }

        @Override
        public void setComments(RDCommentList comments) {
            mComments = comments;
            mComments.setHoldingReview(this);
        }

        @Override
        public void setRating(float rating) {
            mRating = new RDRating(rating, this);
        }

        @Override
        public void setSubject(String subject) {
            mSubject = new RDSubject(subject, this);
        }
    }
}
