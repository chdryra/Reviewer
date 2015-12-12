package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
import com.chdryra.android.reviewer.Database.Interfaces.RowReview;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReviewImpl implements RowReview {
    private ReviewId mReviewId;
    private String mParentId;
    private String mAuthorId;
    private long mPublishDate;
    private String mSubject;
    private float mRating;
    private int mRatingWeight;
    private boolean mRatingIsAverage;

    //Constructors
    public RowReviewImpl(Review review) {
        mReviewId = review.getReviewId();
        mAuthorId = review.getAuthor().getUserId();
        mPublishDate = review.getPublishDate().getTime();
        mSubject = review.getSubject().getSubject();
        mRating = review.getRating().getRating();
        mRatingWeight = review.getRating().getRatingWeight();
        mRatingIsAverage = review.isRatingAverageOfCriteria();
    }

    public RowReviewImpl(DataCriterionReview criterion) {
        this(criterion.getReview());
        mParentId = criterion.getReviewId();
    }

    //Via reflection
    public RowReviewImpl() {
    }

    public RowReviewImpl(Cursor cursor) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mParentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PARENT_ID));
        mAuthorId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_AUTHOR_ID));
        mPublishDate = cursor.getLong(cursor.getColumnIndexOrThrow(COLUMN_PUBLISH_DATE));
        mSubject = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SUBJECT));
        mRating = cursor.getFloat(cursor.getColumnIndexOrThrow(COLUMN_RATING));
        mRatingWeight = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RATING_WEIGHT));
        mRatingIsAverage = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RATING_IS_AVERAGE)) == 1;
    }

    @Override
    public ReviewId getReviewId() {
        return mReviewId;
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
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_REVIEW_ID, mReviewId);
        values.put(COLUMN_PARENT_ID, mParentId);
        values.put(COLUMN_AUTHOR_ID, mAuthorId);
        values.put(COLUMN_PUBLISH_DATE, mPublishDate);
        values.put(COLUMN_SUBJECT, mSubject);
        values.put(COLUMN_RATING, mRating);
        values.put(COLUMN_RATING_WEIGHT, mRating);
        values.put(COLUMN_RATING_IS_AVERAGE, mRatingIsAverage);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validateString(getRowId()) && mRatingWeight >= 1;
    }
}
