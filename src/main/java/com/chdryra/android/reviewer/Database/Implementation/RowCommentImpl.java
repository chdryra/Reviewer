package com.chdryra.android.reviewer.Database.Implementation;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.Database.Interfaces.RowComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowCommentImpl implements RowComment {
    private static final String SEPARATOR = ":";

    private String mCommentId;
    private String mReviewId;
    private String mComment;
    private boolean mIsHeadline;

    //Constructors
    public RowCommentImpl(DataComment comment, int index) {
        mReviewId = comment.getReviewId();
        mCommentId = mReviewId + SEPARATOR + "c" + String.valueOf(index);
        mComment = comment.getComment();
        mIsHeadline = comment.isHeadline();
    }

    //Via reflection
    public RowCommentImpl() {
    }

    public RowCommentImpl(Cursor cursor) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT_ID));
        mComment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT));
        mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_HEADLINE)) == 1;
    }

    //Overridden

    @Override
    public String getReviewId() {
        return mReviewId;
    }

    @Override
    public String getComment() {
        return mComment;
    }

    @Override
    public boolean isHeadline() {
        return mIsHeadline;
    }

    @Override
    public String getRowId() {
        return mCommentId;
    }

    @Override
    public String getRowIdColumnName() {
        return COLUMN_COMMENT_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COLUMN_COMMENT_ID, mCommentId);
        values.put(COLUMN_REVIEW_ID, mReviewId);
        values.put(COLUMN_COMMENT, mComment);
        values.put(COLUMN_IS_HEADLINE, mIsHeadline);

        return values;
    }

    @Override
    public boolean hasData(DataValidator validator) {
        return validator.validate(this);
    }
}
