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
import com.chdryra.android.reviewer.RDComment;
import com.chdryra.android.reviewer.RDFact;
import com.chdryra.android.reviewer.RDId;
import com.chdryra.android.reviewer.RDImage;
import com.chdryra.android.reviewer.RDList;
import com.chdryra.android.reviewer.RDLocation;
import com.chdryra.android.reviewer.RDRating;
import com.chdryra.android.reviewer.RDSubject;
import com.chdryra.android.reviewer.RDUrl;
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
        private RDId               mId;
        private RDSubject          mSubject;
        private RDRating           mRating;
        private ReviewNode         mNode;
        private RDList<RDComment>  mComments;
        private RDList<RDFact>     mFacts;
        private RDList<RDImage>    mImages;
        private RDList<RDUrl>      mUrls;
        private RDList<RDLocation> mLocations;

        private MockReviewEditable() {
            mId = RDId.generateId();
            mSubject = new RDSubject("MockReviewEditable", this);
            mRating = new RDRating(3f, this);
            mNode = FactoryReview.createReviewNodeExpandable(this);
            mComments = new RDList<RDComment>();
            mFacts = new RDList<RDFact>();
            mImages = new RDList<RDImage>();
            mUrls = new RDList<RDUrl>();
            mLocations = new RDList<RDLocation>();
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
        public RDList<RDComment> getComments() {
            return mComments;
        }

        @Override
        public boolean hasComments() {
            return mComments.size() > 0;
        }

        @Override
        public RDList<RDFact> getFacts() {
            return mFacts;
        }

        @Override
        public boolean hasFacts() {
            return mFacts.size() > 0;
        }

        @Override
        public RDList<RDImage> getImages() {
            return mImages;
        }

        @Override
        public boolean hasImages() {
            return mImages.size() > 0;
        }

        @Override
        public RDList<RDUrl> getURLs() {
            return mUrls;
        }

        @Override
        public boolean hasUrls() {
            return mUrls.size() > 0;
        }

        @Override
        public RDList<RDLocation> getLocations() {
            return mLocations;
        }

        @Override
        public boolean hasLocations() {
            return mLocations.size() > 0;
        }

        @Override
        public void setLocations(RDList<RDLocation> locations) {
            mLocations = locations;
            mLocations.setHoldingReview(this);
        }

        @Override
        public void setURLs(RDList<RDUrl> url) {
            mUrls = url;
            mUrls.setHoldingReview(this);
        }

        @Override
        public void setImages(RDList<RDImage> images) {
            mImages = images;
            mImages.setHoldingReview(this);
        }

        @Override
        public void setFacts(RDList<RDFact> facts) {
            mFacts = facts;
            mFacts.setHoldingReview(this);
        }

        @Override
        public void setComments(RDList<RDComment> comment) {
            mComments = comment;
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
