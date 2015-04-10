/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 9 April, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Controller.DataValidator;
import com.chdryra.android.reviewer.Model.Review;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowReview implements ReviewerDbRow.TableRow {
    public static String REVIEW_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_REVIEW_ID;
    public static String AUTHOR_ID    = ReviewerDbContract.TableReviews.COLUMN_NAME_AUTHOR_ID;
    public static String PUBLISH_DATE = ReviewerDbContract.TableReviews
            .COLUMN_NAME_PUBLISH_DATE;
    public static String SUBJECT      = ReviewerDbContract.TableReviews.COLUMN_NAME_SUBJECT;
    public static String RATING       = ReviewerDbContract.TableReviews.COLUMN_NAME_RATING;

    private String mReviewId;
    private String mAuthorId;
    private long   mPublishDate;
    private String mSubject;
    private float  mRating;

    public RowReview() {
    }

    public RowReview(Review review) {
        mReviewId = review.getId().toString();
        mAuthorId = review.getAuthor().getUserId().toString();
        mPublishDate = review.getPublishDate().getTime();
        mSubject = review.getSubject().get();
        mRating = review.getRating().get();
    }

    public RowReview(Cursor cursor) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
        mAuthorId = cursor.getString(cursor.getColumnIndexOrThrow(AUTHOR_ID));
        mPublishDate = cursor.getLong(cursor.getColumnIndexOrThrow(PUBLISH_DATE));
        mSubject = cursor.getString(cursor.getColumnIndexOrThrow(SUBJECT));
        mRating = cursor.getFloat(cursor.getColumnIndexOrThrow(RATING));
    }

    @Override
    public String getRowId() {
        return mReviewId;
    }

    @Override
    public String getRowIdColumnName() {
        return REVIEW_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(REVIEW_ID, mReviewId);
        values.put(AUTHOR_ID, mAuthorId);
        values.put(PUBLISH_DATE, mPublishDate);
        values.put(SUBJECT, mSubject);
        values.put(RATING, mRating);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }
}