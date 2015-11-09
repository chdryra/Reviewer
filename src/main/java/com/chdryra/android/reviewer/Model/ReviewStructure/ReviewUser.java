/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 February, 2015
 */

package com.chdryra.android.reviewer.Model.ReviewStructure;

import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewData.MdFactList;
import com.chdryra.android.reviewer.Model.ReviewData.MdImageList;
import com.chdryra.android.reviewer.Model.ReviewData.MdLocationList;
import com.chdryra.android.reviewer.Model.ReviewData.MdRating;
import com.chdryra.android.reviewer.Model.ReviewData.MdSubject;
import com.chdryra.android.reviewer.Model.ReviewData.PublishDate;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;
import com.chdryra.android.reviewer.Model.UserData.Author;

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
    private final ReviewId mId;
    private final Author mAuthor;
    private final PublishDate mPublishDate;
    private final MdSubject mSubject;
    private final MdRating mRating;
    private final MdCriterionList mCriteria;
    private final MdCommentList mComments;
    private final MdImageList mImages;
    private final MdFactList mFacts;
    private final MdLocationList mLocations;
    private final boolean mRatingIsAverage;

    private ReviewNode mNode;

    //Constructors
    public ReviewUser(ReviewId id, Author author, PublishDate publishDate, MdSubject subject,
                      MdRating rating, MdCommentList comments,
                      MdImageList images,
                      MdFactList facts,
                      MdLocationList locations,
                      MdCriterionList criteria,
                      boolean ratingIsAverage,
                      FactoryReview reviewFactory) {
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
        mNode = reviewFactory.createReviewTreeNode(this, false).createTree();
    }

    //Overridden
    @Override
    public MdSubject getSubject() {
        return mSubject;
    }

    @Override
    public MdRating getRating() {
        return mRating;
    }

    @Override
    public Author getAuthor() {
        return mAuthor;
    }

    @Override
    public PublishDate getPublishDate() {
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
    public MdCriterionList getCriteria() {
        return mCriteria;
    }

    @Override
    public MdCommentList getComments() {
        return mComments;
    }

    @Override
    public MdFactList getFacts() {
        return mFacts;
    }

    @Override
    public MdImageList getImages() {
        return mImages;
    }

    @Override
    public MdLocationList getLocations() {
        return mLocations;
    }

    @Override
    public ReviewId getId() {
        return mId;
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
