/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;

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
                      FactoryReviewNode nodeFactory) {
        mId = id;

        checkId(author);
        checkId(publishDate);
        checkId(subject);
        checkId(rating);
        checkId(comments);
        checkId(images);
        checkId(facts);
        checkId(locations);
        checkId(criteria);

        mAuthor = author;
        mPublishDate = publishDate;
        mSubject = subject;
        mRatingIsAverage = ratingIsAverage;

        mRating = rating;
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCriteria = criteria;

        mNode = nodeFactory.createReviewNode(this, false);
    }

    private void checkId(HasReviewId datum) {
        if(!mId.equals(datum.getReviewId())) {
            throw new IllegalArgumentException("Datum should have same Id as review!");
        }
    }
    
    @Override
    public ReviewId getReviewId() {
        return mId;
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
        return (!mLocations.equals(that.mLocations));
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