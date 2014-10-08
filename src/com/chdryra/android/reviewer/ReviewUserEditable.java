/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Editable version of ReviewUser. Returns a ReviewUser when published. ReviewTreeEditable uses
 * this as the root node.
 *
 * @see com.chdryra.android.reviewer.ReviewUser
 * @see com.chdryra.android.reviewer.ReviewTreeEditable
 */
class ReviewUserEditable extends ReviewEditable {
    private ReviewNode mNode;

    private RDId      mID;
    private RDSubject mSubject;
    private RDRating  mRating;

    private RDList<RDComment>  mComments;
    private RDList<RDImage>    mImages;
    private RDList<RDFact>     mFacts;
    private RDList<RDUrl>      mURLs;
    private RDList<RDLocation> mLocations;

    public ReviewUserEditable(String subject) {
        init(subject);
    }

    private void init(String subject) {
        mID = RDId.generateId();
        mSubject = new RDSubject(subject, this);
        mRating = new RDRating(0, this);

        //Null option data
        mComments = new RDList<RDComment>();
        mImages = new RDList<RDImage>();
        mLocations = new RDList<RDLocation>();
        mFacts = new RDList<RDFact>();
        mURLs = new RDList<RDUrl>();

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
    public void setSubject(String subject) {
        mSubject = new RDSubject(subject, this);
    }

    @Override
    public RDRating getRating() {
        return mRating;
    }

    @Override
    public void setRating(float rating) {
        mRating = new RDRating(rating, this);
    }

    @Override
    public ReviewNode getReviewNode() {
        return mNode;
    }

    @Override
    public Review publish(ReviewTreePublisher publisher) {
        return FactoryReview.createReviewUser(this).publish(publisher);
    }

    @Override
    public RDList<RDComment> getComments() {
        return mComments;
    }

    @Override
    public void setComments(RDList<RDComment> comments) {
        mComments = processData(comments, new RDList<RDComment>());
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
    public void setFacts(RDList<RDFact> facts) {
        mFacts = processData(facts, new RDList<RDFact>());
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
    public void setImages(RDList<RDImage> images) {
        mImages = processData(images, new RDList<RDImage>());
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
    public void setURLs(RDList<RDUrl> urls) {
        mURLs = processData(urls, new RDList<RDUrl>());
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
    public void setLocations(RDList<RDLocation> locations) {
        mLocations = processData(locations, new RDList<RDLocation>());
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

        ReviewUserEditable objReview = (ReviewUserEditable) obj;
        return mID.equals(objReview.mID);
    }

    @Override
    public int hashCode() {
        return mID.hashCode();
    }

    private <T extends RData> RDList<T> processData(RDList<T> newData, RDList<T> ifNull) {
        RDList<T> member;
        if (newData != null) {
            member = newData;
        } else {
            member = ifNull;
        }

        member.setHoldingReview(this);

        return member;
    }
}
