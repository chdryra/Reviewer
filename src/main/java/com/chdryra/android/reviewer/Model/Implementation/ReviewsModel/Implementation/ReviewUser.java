/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.ReviewNode;

import junit.framework.Assert;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * The fundamental {@link Review} implementation that holds the
 * review data.
 */
public class ReviewUser implements Review {
    private final MdReviewId mId;
    private final MdAuthor mAuthor;
    private final MdDate mPublishDate;
    private final MdSubject mSubject;
    private final MdRating mRating;
    private final MdDataList<MdCriterion> mCriteria;
    private final MdDataList<MdComment> mComments;
    private final MdDataList<MdImage> mImages;
    private final MdDataList<MdFact> mFacts;
    private final MdDataList<MdLocation> mLocations;
    private final boolean mRatingIsAverage;

    private ReviewNode mNode;

    //Constructors
    public ReviewUser(MdReviewId id, MdAuthor author, MdDate publishDate, MdSubject subject,
                      MdRating rating, MdDataList<MdComment> comments,
                      MdDataList<MdImage>  images,
                      MdDataList<MdFact> facts,
                      MdDataList<MdLocation> locations,
                      MdDataList<MdCriterion> criteria,
                      boolean ratingIsAverage,
                      FactoryReviews reviewsFactory) {
        mId = id;
        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = subject;
        mRatingIsAverage = ratingIsAverage;

        mRating = rating;
        Assert.assertEquals(mId.toString(), subject.getReviewId());
        Assert.assertEquals(mId.toString(), rating.getReviewId());
        Assert.assertEquals(mId.toString(), comments.getReviewId());
        Assert.assertEquals(mId.toString(), images.getReviewId());
        Assert.assertEquals(mId.toString(), facts.getReviewId());
        Assert.assertEquals(mId.toString(), locations.getReviewId());
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCriteria = criteria;

        mNode = reviewsFactory.createReviewNode(this, false);
    }

    //Overridden

    @Override
    public String getReviewId() {
        return mId.toString();
    }

    @Override
    public MdSubject getSubject() {
        return mSubject;
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public MdAuthor getAuthor() {
        return mAuthor;
    }

    @Override
    public MdDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return mNode;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mRatingIsAverage;
    }

    @Override
    public MdDataList<MdCriterion> getCriteria() {
        return mCriteria;
    }

    @Override
    public MdDataList<MdComment> getComments() {
        return mComments;
    }

    @Override
    public MdDataList<MdFact> getFacts() {
        return mFacts;
    }

    @Override
    public MdDataList<MdImage> getImages() {
        return mImages;
    }

    @Override
    public MdDataList<MdImage> getCovers() {
        MdDataList<MdImage> covers = new MdDataList<>(mId);
        for (MdImage image : getImages()) {
            if (image.isCover()) covers.add(image);
        }

        return covers;
    }

    @Override
    public MdDataList<MdLocation> getLocations() {
        return mLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewUser)) return false;

        ReviewUser that = (ReviewUser) o;

        if (mRatingIsAverage != that.mRatingIsAverage) return false;
        if (!mId.equals(that.mId)) return false;
        if (!mAuthor.equals(that.mAuthor)) return false;
        if (!mPublishDate.equals(that.mPublishDate)) return false;
        if (!mSubject.equals(that.mSubject)) return false;
        if (!mRating.equals(that.mRating)) return false;
        if (!mCriteria.equals(that.mCriteria)) return false;
        if (!mComments.equals(that.mComments)) return false;
        if (!mImages.equals(that.mImages)) return false;
        if (!mFacts.equals(that.mFacts)) return false;
        return mLocations.equals(that.mLocations);

    }

    @Override
    public int hashCode() {
        int result = mId.hashCode();
        result = 31 * result + mAuthor.hashCode();
        result = 31 * result + mPublishDate.hashCode();
        result = 31 * result + mSubject.hashCode();
        result = 31 * result + mRating.hashCode();
        result = 31 * result + mCriteria.hashCode();
        result = 31 * result + mComments.hashCode();
        result = 31 * result + mImages.hashCode();
        result = 31 * result + mFacts.hashCode();
        result = 31 * result + mLocations.hashCode();
        result = 31 * result + (mRatingIsAverage ? 1 : 0);
        return result;
    }
}
