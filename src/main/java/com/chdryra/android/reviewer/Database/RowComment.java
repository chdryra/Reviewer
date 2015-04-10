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
import com.chdryra.android.reviewer.Model.MdCommentList;
import com.chdryra.android.reviewer.Model.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class RowComment implements MdDataRow<MdCommentList.MdComment> {
    public static String COMMENT_ID  = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT_ID;
    public static String REVIEW_ID   = ReviewerDbContract.TableComments.COLUMN_NAME_REVIEW_ID;
    public static String COMMENT     = ReviewerDbContract.TableComments.COLUMN_NAME_COMMENT;
    public static String IS_HEADLINE = ReviewerDbContract.TableComments.COLUMN_NAME_IS_HEADLINE;

    private String  mCommentId;
    private String  mReviewId;
    private String  mComment;
    private boolean mIsHeadline;

    public RowComment() {
    }

    public RowComment(MdCommentList.MdComment comment, int index) {
        mReviewId = comment.getReviewId().toString();
        mCommentId = mReviewId + ReviewerDbRow.SEPARATOR + "c" + String.valueOf(index);
        mComment = comment.getComment();
        mIsHeadline = comment.isHeadline();
    }

    public RowComment(Cursor cursor) {
        mReviewId = cursor.getString(cursor.getColumnIndexOrThrow(REVIEW_ID));
        mCommentId = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT_ID));
        mComment = cursor.getString(cursor.getColumnIndexOrThrow(COMMENT));
        mIsHeadline = cursor.getInt(cursor.getColumnIndexOrThrow(IS_HEADLINE)) == 1;
    }

    @Override
    public String getRowId() {
        return mCommentId;
    }

    @Override
    public String getRowIdColumnName() {
        return COMMENT_ID;
    }

    @Override
    public ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(COMMENT_ID, mCommentId);
        values.put(REVIEW_ID, mReviewId);
        values.put(COMMENT, mComment);
        values.put(IS_HEADLINE, mIsHeadline);

        return values;
    }

    @Override
    public boolean hasData() {
        return DataValidator.validateString(getRowId());
    }

    @Override
    public MdCommentList.MdComment toMdData() {
        return new MdCommentList.MdComment(mComment, mIsHeadline, ReviewId.fromString(mReviewId));
    }
}