/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImpl extends RowTableBasic implements RowReview {
    private String mReviewId;
    private String mParentId;
    private String mAuthorId;
    private long mPublishDate;
    private String mSubject;
    private float mRating;
    private int mRatingWeight;
    private boolean mRatingIsAverage;

    private boolean mValidIsAverage = true;

    public RowReviewImpl(Review review) {
        mReviewId = review.getReviewId().toString();
        mAuthorId = review.getAuthor().getUserId().toString();
        mPublishDate = review.getPublishDate().getTime();
        mSubject = review.getSubject().getSubject();
        mRating = review.getRating().getRating();
        mRatingWeight = review.getRating().getRatingWeight();
        mRatingIsAverage = review.isRatingAverageOfCriteria();
    }

    public RowReviewImpl(DataCriterionReview criterion) {
        this(criterion.getReview());
        mParentId = criterion.getReviewId().toString();
    }

    //Via reflection
    public RowReviewImpl() {
    }

    public RowReviewImpl(RowValues values) {
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mParentId = values.getValue(PARENT_ID.getName(), PARENT_ID.getType());
        mAuthorId = values.getValue(USER_ID.getName(), USER_ID.getType());

        Long time = values.getValue(PUBLISH_DATE.getName(), PUBLISH_DATE.getType());
        mPublishDate = time != null ? time : 0l;

        mSubject = values.getValue(SUBJECT.getName(), SUBJECT.getType());

        Float rating = values.getValue(RATING.getName(), RATING.getType());
        mRating = rating != null ? rating : -1f;

        Integer weight = values.getValue(RATING_WEIGHT.getName(), RATING_WEIGHT.getType());
        mRatingWeight = weight != null ? weight : 0;

        Boolean average = values.getValue(IS_AVERAGE.getName(), IS_AVERAGE.getType());
        if(average == null) mValidIsAverage = false;
        mRatingIsAverage = mValidIsAverage && average;
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
    }

    @Override
    public String getParentId() {
        return mParentId;
    }

    @Override
    public boolean isRatingIsAverage() {
        return mRatingIsAverage;
    }

    @Override
    public long getTime() {
        return getPublishDate();
    }

    @Override
    public int getRatingWeight() {
        return mRatingWeight;
    }

    @Override
    public String getAuthorId() {
        return mAuthorId;
    }

    @Override
    public long getPublishDate() {
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
    public String getRowId() {
        return mReviewId;
    }

    @Override
    public String getRowIdColumnName() {
        return REVIEW_ID.getName();
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return mPublishDate > 0 && mRating != -1f && mRatingWeight > 0 && mValidIsAverage
                && validator.validate(getReviewId()) &&
                validator.validateString(getSubject())  &&
                validator.validateString(getAuthorId());
    }

    @Override
    protected int size() {
        return 8;
    }

    @Override
    protected RowEntry<?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(REVIEW_ID, mReviewId);
        } else if(position == 1) {
            return new RowEntryImpl<>(PARENT_ID, mParentId);
        } else if(position == 2) {
            return new RowEntryImpl<>(USER_ID, mAuthorId);
        } else if(position == 3) {
            return new RowEntryImpl<>(PUBLISH_DATE, mPublishDate);
        } else if(position == 4) {
            return new RowEntryImpl<>(SUBJECT, mSubject);
        } else if(position == 5) {
            return new RowEntryImpl<>(RATING, mRating);
        } else if(position == 6) {
            return new RowEntryImpl<>(RATING_WEIGHT, mRatingWeight);
        } else if(position == 7) {
            return new RowEntryImpl<>(IS_AVERAGE, mRatingIsAverage);
        } else {
            throw noElement();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowReviewImpl)) return false;

        RowReviewImpl that = (RowReviewImpl) o;

        if (mPublishDate != that.mPublishDate) return false;
        if (Float.compare(that.mRating, mRating) != 0) return false;
        if (mRatingWeight != that.mRatingWeight) return false;
        if (mRatingIsAverage != that.mRatingIsAverage) return false;
        if (mValidIsAverage != that.mValidIsAverage) return false;
        if (mReviewId != null ? !mReviewId.equals(that.mReviewId) : that.mReviewId != null)
            return false;
        if (mParentId != null ? !mParentId.equals(that.mParentId) : that.mParentId != null)
            return false;
        if (mAuthorId != null ? !mAuthorId.equals(that.mAuthorId) : that.mAuthorId != null)
            return false;
        return !(mSubject != null ? !mSubject.equals(that.mSubject) : that.mSubject != null);

    }

    @Override
    public int hashCode() {
        int result = mReviewId != null ? mReviewId.hashCode() : 0;
        result = 31 * result + (mParentId != null ? mParentId.hashCode() : 0);
        result = 31 * result + (mAuthorId != null ? mAuthorId.hashCode() : 0);
        result = 31 * result + (int) (mPublishDate ^ (mPublishDate >>> 32));
        result = 31 * result + (mSubject != null ? mSubject.hashCode() : 0);
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + mRatingWeight;
        result = 31 * result + (mRatingIsAverage ? 1 : 0);
        result = 31 * result + (mValidIsAverage ? 1 : 0);
        return result;
    }
}
