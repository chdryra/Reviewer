package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;
import android.database.Cursor;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataComment;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewData.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewData.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowComment implements MdDataRow<MdCommentList.MdComment>, DataComment {
    public static final String COLUMN_COMMENT_ID = "comment_id";
    public static final String COLUMN_REVIEW_ID = "review_id";
    public static final String COLUMN_COMMENT = "comment";
    public static final String COLUMN_IS_HEADLINE = "is_headline";

    private static final String SEPARATOR = ":";

    private String mCommentId;
    private String mReviewId;
    private String mComment;
    private boolean mIsHeadline;

    //Constructors
    public RowComment(MdCommentList.MdComment comment, int index) {
        mReviewId = comment.getReviewId().toString();
        mCommentId = mReviewId + SEPARATOR + "c" + String.valueOf(index);
        mComment = comment.getComment();
        mIsHeadline = comment.isHeadline();
    }

    //Via reflection
    public RowComment() {
    }

    public RowComment(Cursor cursor) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_REVIEW_ID));
        mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT_ID));
        mComment = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_COMMENT));
        mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_IS_HEADLINE)) == 1;
    }

    //Overridden

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

    @Override
    public MdCommentList.MdComment toMdData() {
        return new MdCommentList.MdComment(mComment, mIsHeadline, ReviewId.fromString(mReviewId));
    }
}
