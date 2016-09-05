/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImpl extends RowTableBasic<RowReview> implements RowReview {
    private String mReviewId;
    private String mAuthorId;
    private long mPublishDate;
    private String mSubject;
    private float mRating;
    private int mRatingWeight;

    private final boolean mValidIsAverage = true;

    public RowReviewImpl(Review review) {
        mReviewId = review.getReviewId().toString();
        mAuthorId = review.getAuthorId().toString();
        mPublishDate = review.getPublishDate().getTime();
        mSubject = review.getSubject().getSubject();
        mRating = review.getRating().getRating();
        mRatingWeight = review.getRating().getRatingWeight();
    }

    //Via reflection
    public RowReviewImpl() {
    }

    public RowReviewImpl(RowValues values) {
        mReviewId = values.getValue(REVIEW_ID.getName(), REVIEW_ID.getType());
        mAuthorId = values.getValue(AUTHOR_ID.getName(), AUTHOR_ID.getType());

        Long time = values.getValue(PUBLISH_DATE.getName(), PUBLISH_DATE.getType());
        mPublishDate = time != null ? time : 0L;

        mSubject = values.getValue(SUBJECT.getName(), SUBJECT.getType());

        Float rating = values.getValue(RATING.getName(), RATING.getType());
        mRating = rating != null ? rating : -1f;

        Integer weight = values.getValue(RATING_WEIGHT.getName(), RATING_WEIGHT.getType());
        mRatingWeight = weight != null ? weight : 0;
    }

    @Override
    public ReviewId getReviewId() {
        return new DatumReviewId(mReviewId);
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
        return mPublishDate > 0 && mRating != -1f && mRatingWeight > 0 && validator.validate
                (getReviewId()) &&
                validator.validateString(getSubject()) &&
                validator.validateString(getAuthorId());
    }

    @Override
    protected int size() {
        return 6;
    }

    @Override
    protected RowEntry<RowReview, ?> getEntry(int position) {
        if(position == 0) {
            return new RowEntryImpl<>(RowReview.class, REVIEW_ID, mReviewId);
        } else if(position == 1) {
            return new RowEntryImpl<>(RowReview.class, AUTHOR_ID, mAuthorId);
        } else if(position == 2) {
            return new RowEntryImpl<>(RowReview.class, PUBLISH_DATE, mPublishDate);
        } else if(position == 3) {
            return new RowEntryImpl<>(RowReview.class, SUBJECT, mSubject);
        } else if(position == 4) {
            return new RowEntryImpl<>(RowReview.class, RATING, mRating);
        } else if(position == 5) {
            return new RowEntryImpl<>(RowReview.class, RATING_WEIGHT, mRatingWeight);
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
        if (mValidIsAverage != that.mValidIsAverage) return false;
        if (!mReviewId.equals(that.mReviewId)) return false;
        if (!mAuthorId.equals(that.mAuthorId)) return false;
        return mSubject.equals(that.mSubject);

    }

    @Override
    public int hashCode() {
        int result = mReviewId.hashCode();
        result = 31 * result + mAuthorId.hashCode();
        result = 31 * result + (int) (mPublishDate ^ (mPublishDate >>> 32));
        result = 31 * result + mSubject.hashCode();
        result = 31 * result + (mRating != +0.0f ? Float.floatToIntBits(mRating) : 0);
        result = 31 * result + mRatingWeight;
        result = 31 * result + (mValidIsAverage ? 1 : 0);
        return result;
    }
}
