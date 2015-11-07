package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowComment implements MdDataRow<MdCommentList.MdComment> {
    public static final String COLUMN_COMMENT_ID = "comment_id";
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_IS_HEADLINE = "is_headline";

    private static final String SEPARATOR = ":";

    private String mCommentId;
    private String mReviewId;
    private String mComment;
    private boolean mIsHeadline;
    private DataValidator mValidator;

    //Constructors
    public RowComment(MdCommentList.MdComment comment, int index, DataValidator validator) {
        mReviewId = comment.getReviewId().toString();
        mCommentId = mReviewId + SEPARATOR + "c" + String.valueOf(index);
        mComment = comment.getComment();
        mIsHeadline = comment.isHeadline();
        mValidator = validator;
    }

    //Via reflection
    public RowComment() {
    }

    public RowComment(Cursor cursor, DataValidator validator) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT_ID));
        mComment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT));
        mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_HEADLINE)) == 1;
        mValidator = validator;
    }

    //Overridden
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
    public boolean hasData() {
        return mValidator != null && mValidator.validateString(getRowId());
    }

    @Override
    public MdCommentList.MdComment toMdData() {
        return new MdCommentList.MdComment(mComment, mIsHeadline, ReviewId.fromString(mReviewId));
    }
}
