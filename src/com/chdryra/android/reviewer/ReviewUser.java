/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

import java.util.Date;

/**
 * The fundamental Review implementation. All published reviews and review nodes are ultimately
 * built out of combinations of this fundamental building block.
 * <p/>
 * <p>
 * This is just a data store. It cannot be edited. The editable version is ReviewUserEditable
 * which will return one of these when published.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ReviewUserEditable
 */
public class ReviewUser implements Review {
    private ReviewNode mNode;

    private RDId      mID;
    private RDSubject mSubject;
    private RDRating  mRating;

    private Author mAuthor;
    private Date   mPublishDate;

    private RDList<RDComment>  mComments;
    private RDList<RDImage>    mImages;
    private RDList<RDFact>     mFacts;
    private RDList<RDUrl>      mURLs;
    private RDList<RDLocation> mLocations;

    public ReviewUser(ReviewEditable review) {
        mID = RDId.generateId();

        mSubject = new RDSubject(review.getSubject().get(), this);
        mRating = new RDRating(review.getRating().get(), this);

        mComments = new RDList<RDComment>(review.getComments(), this);
        mImages = new RDList<RDImage>(review.getImages(), this);
        mFacts = new RDList<RDFact>(review.getFacts(), this);
        mURLs = new RDList<RDUrl>(review.getURLs(), this);
        mLocations = new RDList<RDLocation>(review.getLocations(), this);

        mNode = FactoryReview.createReviewNodeAlone(this);
    }

    @Override
    public RDId getId() {
        return mID;
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
    public Review publish(ReviewTreePublisher publisher) {
        if (!isPublished()) {
            mAuthor = publisher.getAuthor();
            mPublishDate = publisher.getPublishDate();
        }

        return this;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public Date getPublishDate() {
        return mPublishDate;
    }

    @Override
    public boolean isPublished() {
        return mAuthor != null && mPublishDate != null;
    }

    @Override
    public RDList<RDComment> getComments() {
        return mComments;
    }

    @Override
    public boolean hasComments() {
        return mComments.hasData();
    }

    @Override
    public RDList<RDFact> getFacts() {
        return mFacts;
    }

    @Override
    public boolean hasFacts() {
        return mFacts.hasData();
    }

    @Override
    public RDList<RDImage> getImages() {
        return mImages;
    }

    @Override
    public boolean hasImages() {
        return mImages.hasData();
    }

    @Override
    public RDList<RDUrl> getURLs() {
        return mURLs;
    }

    @Override
    public boolean hasUrls() {
        return mURLs.hasData();
    }

    @Override
    public RDList<RDLocation> getLocations() {
        return mLocations;
    }

    @Override
    public boolean hasLocations() {
        return mLocations.hasData();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }

        ReviewUser objReview = (ReviewUser) obj;
        return mID.equals(objReview.mID);
    }

    @Override
    public int hashCode() {
        return mID.hashCode();
    }
}
