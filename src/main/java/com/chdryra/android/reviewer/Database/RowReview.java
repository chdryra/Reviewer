package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdCriterionList;
import com.chdryra.android.reviewer.Model.ReviewStructure.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReview implements ReviewDataRow {
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_PARENT_ID = "parent_id";
    public static final String COLUMN_AUTHOR_ID = "author_id";
    public static final String COLUMN_PUBLISH_DATE = "publish_date";
    public static final String COLUMN_SUBJECT = "subject";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RATING_WEIGHT = "rating_weight";
    public static final String COLUMN_RATING_IS_AVERAGE = "rating_is_average";

    private String mReviewId;
    private String mParentId;
    private String mAuthorId;
    private long mPublishDate;
    private String mSubject;
    private float mRating;
    private int mRatingWeight;
    private boolean mRatingIsAverage;

    //Constructors
    public RowReview(Review review) {
        mReviewId = review.getId().toString();
        mAuthorId = review.getAuthor().getUserId();
        mPublishDate = review.getPublishDate().getDate().getTime();
        mSubject = review.getSubject().get();
        mRating = review.getRating().getValue();
        mRatingWeight = review.getRating().getWeight();
        mRatingIsAverage = review.isRatingAverageOfCriteria();
    }

    public RowReview(MdCriterionList.MdCriterion criterion) {
        this(criterion.getReview());
        mParentId = criterion.getReviewId().toString();
    }

    //Via reflection
    public RowReview() {
    }

    public RowReview(Cursor cursor) {
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
    public String getReviewId() {
        return mReviewId;
    }

    public String getParentId() {
        return mParentId;
    }

    public String getAuthorId() {
        return mAuthorId;
    }

    public long getPublishDate() {
        return mPublishDate;
    }

    public String getSubject() {
        return mSubject;
    }

    public float getRating() {
        return mRating;
    }

    public int getRatingWeight() {
        return mRatingWeight;
    }

    public boolean isRatingIsAverage() {
        return mRatingIsAverage;
    }

    //Overridden
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
