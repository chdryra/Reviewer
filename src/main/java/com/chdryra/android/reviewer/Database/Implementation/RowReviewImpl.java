package com.chdryra.android.reviewer.Database.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImpl implements RowReview {
    private String mReviewId;
    private String mParentId;
    private String mAuthorId;
    private long mPublishDate;
    private String mSubject;
    private float mRating;
    private int mRatingWeight;
    private boolean mRatingIsAverage;

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
        mReviewId = values.getString(COLUMN_REVIEW_ID);
        mParentId = values.getString(COLUMN_PARENT_ID);
        mAuthorId = values.getString(COLUMN_AUTHOR_ID);
        mPublishDate = values.getLong(COLUMN_PUBLISH_DATE);
        mSubject = values.getString(COLUMN_SUBJECT);
        mRating = values.getFloat(COLUMN_RATING);
        mRatingWeight = values.getInteger(COLUMN_RATING_WEIGHT);
        mRatingIsAverage = values.getBoolean(COLUMN_RATING_IS_AVERAGE);
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
        return COLUMN_REVIEW_ID;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(getRowId()) && mRatingWeight >= 1;
    }
}
