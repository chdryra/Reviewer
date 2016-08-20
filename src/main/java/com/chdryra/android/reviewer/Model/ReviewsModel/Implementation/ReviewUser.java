/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 13/02/2015
 * Email: rizwan.choudrey@gmail.com
 */

/**
 * The fundamental {@link Review} implementation that holds the
 * review data.
 */
public class ReviewUser extends ReviewStatic {
    private final ReviewId mId;

    private final DataAuthorId mAuthor;
    private final DataDate mPublishDate;
    private final DataSubject mSubject;
    private final DataRating mRating;

    private final IdableList<? extends DataCriterion> mCriteria;
    private final IdableList<? extends DataComment> mComments;
    private final IdableList<? extends DataImage> mImages;
    private final IdableList<? extends DataFact> mFacts;
    private final IdableList<? extends DataLocation> mLocations;

    public ReviewUser(ReviewId id,
                      DataAuthorId author,
                      DataDate publishDate,
                      DataSubject subject,
                      DataRating rating,
                      IdableList<? extends DataComment> comments,
                      IdableList<? extends DataImage>  images,
                      IdableList<? extends DataFact> facts,
                      IdableList<? extends DataLocation> locations,
                      IdableList<? extends DataCriterion> criteria) {
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

        mRating = rating;
        mComments = comments;
        mImages = images;
        mFacts = facts;
        mLocations = locations;
        mCriteria = criteria;
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
    public DataSubject getSubject() {
        return mSubject;
    }

    @Override
    public DataRating getRating() {
        return mRating;
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mAuthor;
    }

    @Override
    public DataDate getPublishDate() {
        return mPublishDate;
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return mCriteria;
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return mComments;
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return mFacts;
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return mImages;
    }

    @Override
    public DataImage getCover() {
        DataImage cover = new DatumImage(mId);
        for (DataImage image : getImages()) {
            if (image.isCover()) cover = image;
        }

        return cover;
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return mLocations;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ReviewUser)) return false;

        ReviewUser that = (ReviewUser) o;

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
        return result;
    }
}
