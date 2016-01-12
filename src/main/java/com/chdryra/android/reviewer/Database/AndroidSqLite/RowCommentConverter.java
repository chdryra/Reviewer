package com.chdryra.android.reviewer.Database.AndroidSqLite;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Database.Interfaces.RowComment;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
class RowCommentConverter implements RowConverter<RowComment> {
    @Override
    public ContentValues convert(RowComment row) {
        ContentValues values = new ContentValues();
        values.put(RowComment.COLUMN_COMMENT_ID, row.getRowId());
        values.put(RowComment.COLUMN_REVIEW_ID, row.getReviewId().toString());
        values.put(RowComment.COLUMN_COMMENT, row.getComment());
        values.put(RowComment.COLUMN_IS_HEADLINE, row.isHeadline());

        return values;
    }
}
