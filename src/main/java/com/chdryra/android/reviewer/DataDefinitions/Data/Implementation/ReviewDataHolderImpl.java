/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DateTime;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewDataHolderImpl implements ReviewDataHolder {
    private final ReviewId mId;
    private final AuthorId mAuthorId;
    private final DateTime mPublishDate;
    private final String mSubject;
    private final float mRating;
    private final int mRatingWeight;
    private final Iterable<? extends DataTag> mTags;
    private final Iterable<? extends DataComment> mComments;
    private final Iterable<? extends DataImage> mImages;
    private final Iterable<? extends DataFact> mFacts;
    private final Iterable<? extends DataLocation> mLocations;
    private final Iterable<? extends DataCriterion> mCriteria;

    public ReviewDataHolderImpl(ReviewId id, AuthorId authorId, DateTime publishDate,
                                String subject, float rating, int ratingWeight,
                                Iterable<? extends DataTag> tags,
                                Iterable<? extends DataComment> comments,
                                Iterable<? extends DataImage> images,
                                Iterable<? extends DataFact> facts,
                                Iterable<? extends DataLocation> locations,
                                Iterable<? extends DataCriterion> criteria) {
        mId = id;
        mAuthorId = authorId;
        mPublishDate = publishDate;
        mSubject = subject;
        mRating = rating;
        mRatingWeight = ratingWeight;
        mTags = tags;
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCriteria = criteria;
    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public AuthorId getAuthorId() {
        return mAuthorId;
    }

    @Override
    public DateTime getPublishDate() {
        return mPublishDate;
    }

    @Override
    public String getSubject() {
        return mSubject;
    }

    @Override
    public float getRating() {
        return mRating;
    }

    @Override
    public int getRatingWeight() {
        return mRatingWeight;
    }

    @Override
    public Iterable<? extends DataTag> getTags() {
        return mTags;
    }

    @Override
    public Iterable<? extends DataComment> getComments() {
        return mComments;
    }

    @Override
    public Iterable<? extends DataImage> getImages() {
        return mImages;
    }

    @Override
    public Iterable<? extends DataFact> getFacts() {
        return mFacts;
    }

    @Override
    public Iterable<? extends DataLocation> getLocations() {
        return mLocations;
    }

    @Override
    public Iterable<? extends DataCriterion> getCriteria() {
        return mCriteria;
    }

    @Override
    public boolean isValid(DataValidator validator) {
        return validator.validate(mId) && validator.validate(mAuthorId)
                && validator.validateString(mSubject) && mRatingWeight > 0;
    }
}
