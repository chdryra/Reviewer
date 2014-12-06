/*
 * Copyright (c) 2014, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 23 September, 2014
 */

package com.chdryra.android.reviewer;

/**
 * Fundamental implementation of {@link Review} that holds the review data. Every node in a review
 * tree eventually wraps one of these.
 * <p/>
 * <p>
 * Gets wrapped in a non-editable {@link Review} when published along with an {@link Author} and
 * publish
 * date.
 * </p>
 *
 * @see com.chdryra.android.reviewer.ReviewNodeAlone
 * @see com.chdryra.android.reviewer.ReviewTree
 */
class ReviewUserEditable extends ReviewEditable {
    private final ReviewNode mNode;

    private final RDId      mID;
    private       RDSubject mSubject;
    private       RDRating  mRating;

    private RDCommentList  mComments;
    private RDImageList    mImages;
    private RDFactList     mFacts;
    private RDUrlList      mURLs;
    private RDLocationList mLocations;

    ReviewUserEditable(String subject) {
        //Core data
        mID = RDId.generateId();
        mSubject = new RDSubject(subject, this);
        mRating = new RDRating(0, this);

        //Null option data
        mComments = new RDCommentList();
        mImages = new RDImageList();
        mLocations = new RDLocationList();
        mFacts = new RDFactList();
        mURLs = new RDUrlList();

        //Internal node representation
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
    public Review publish(PublisherReviewTree publisher) {
        return FactoryReview.createReview(publisher.getAuthor(), publisher.getPublishDate(),
                getReviewNode());
    }

    @Override
    public RDCommentList getComments() {
        return mComments;
    }

    @Override
    public void setComments(RDCommentList comments) {
        mComments = processData(comments, new RDCommentList());
    }

    @Override
    public boolean hasComments() {
        return mComments.hasData();
    }

    @Override
    public RDFactList getFacts() {
        return mFacts;
    }

    @Override
    public void setFacts(RDFactList facts) {
        mFacts = processData(facts, new RDFactList());
    }

    @Override
    public boolean hasFacts() {
        return mFacts.hasData();
    }

    @Override
    public RDImageList getImages() {
        return mImages;
    }

    @Override
    public void setImages(RDImageList images) {
        mImages = processData(images, new RDImageList());
    }

    @Override
    public boolean hasImages() {
        return mImages.hasData();
    }

    @Override
    public RDUrlList getURLs() {
        return mURLs;
    }

    @Override
    public void setURLs(RDUrlList urls) {
        mURLs = processData(urls, new RDUrlList());
    }

    @Override
    public boolean hasUrls() {
        return mURLs.hasData();
    }

    @Override
    public RDLocationList getLocations() {
        return mLocations;
    }

    @Override
    public void setLocations(RDLocationList locations) {
        mLocations = processData(locations, new RDLocationList());
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

    private <T extends RDList> T processData(T newData, T ifNull) {
        T member;
        member = newData == null ? ifNull : newData;
        member.setHoldingReview(this);

        return member;
    }
}
