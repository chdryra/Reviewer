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
public class ReviewUserEditable extends ReviewEditable {
    private final ReviewNode mNode;

    private final ReviewId  mID;
    private       MdSubject mSubject;
    private       MdRating  mRating;

    private MdCommentList  mComments;
    private MdImageList    mImages;
    private MdFactList     mFacts;
    private MdUrlList      mURLs;
    private MdLocationList mLocations;

    public ReviewUserEditable(String subject) {
        //Core data
        mID = ReviewId.generateId();
        mSubject = new MdSubject(subject, this);
        mRating = new MdRating(0, this);

        //Null option data
        mComments = new MdCommentList(this);
        mImages = new MdImageList(this);
        mLocations = new MdLocationList(this);
        mFacts = new MdFactList(this);
        mURLs = new MdUrlList(this);

        //Internal node representation
        mNode = FactoryReview.createReviewNodeAlone(this);
    }

    @Override
    public ReviewId getId() {
        return mID;
    }

    @Override
    public MdSubject getSubject() {
        return mSubject;
    }

    @Override
    public void setSubject(String subject) {
        mSubject = new MdSubject(subject, this);
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public void setRating(float rating) {
        mRating = new MdRating(rating, this);
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
    public MdCommentList getComments() {
        return mComments;
    }

    @Override
    public <T extends DataComment> void setComments(Iterable<T> comments) {
        if (mComments.hasData()) mComments = new MdCommentList(this);
        for (DataComment comment : comments) {
            mComments.add(new MdCommentList.MdComment(comment.getComment(), this));
        }
    }

    @Override
    public boolean hasComments() {
        return mComments.hasData();
    }

    @Override
    public MdFactList getFacts() {
        return mFacts;
    }

    @Override
    public <T extends DataFact> void setFacts(Iterable<T> facts) {
        if (mFacts.hasData()) mFacts = new MdFactList(this);
        for (DataFact fact : facts) {
            mFacts.add(new MdFactList.MdFact(fact.getLabel(), fact.getValue(), this));
        }
    }

    @Override
    public boolean hasFacts() {
        return mFacts.hasData();
    }

    @Override
    public MdImageList getImages() {
        return mImages;
    }

    @Override
    public <T extends DataImage> void setImages(Iterable<T> images) {
        if (mImages.hasData()) mImages = new MdImageList(this);
        for (DataImage image : images) {
            mImages.add(new MdImageList.MdImage(image.getBitmap(), image.getLatLng(),
                    image.getCaption(), image.isCover(), this));
        }
    }

    @Override
    public boolean hasImages() {
        return mImages.hasData();
    }

    @Override
    public MdUrlList getUrls() {
        return mURLs;
    }

    @Override
    public <T extends DataUrl> void setUrls(Iterable<T> urls) {
        if (mURLs.hasData()) mURLs = new MdUrlList(this);
        for (DataUrl url : urls) {
            mURLs.add(new MdUrlList.MdUrl(url.getUrl(), this));
        }
    }

    @Override
    public boolean hasUrls() {
        return mURLs.hasData();
    }

    @Override
    public MdLocationList getLocations() {
        return mLocations;
    }

    @Override
    public <T extends DataLocation> void setLocations(Iterable<T> locations) {
        if (mLocations.hasData()) mLocations = new MdLocationList(this);
        for (DataLocation location : locations) {
            mLocations.add(new MdLocationList.MdLocation(location.getLatLng(),
                    location.getName(), this));
        }
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
}
