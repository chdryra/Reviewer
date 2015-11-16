package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataComment;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowComment extends ReviewDataRow, DataComment {
    String COLUMN_COMMENT_ID = "comment_id";
    String COLUMN_REVIEW_ID = "review_id";
    String COLUMN_COMMENT = "comment";
    String COLUMN_IS_HEADLINE = "is_headline";

    @Override
    String getComment();

    @Override
    boolean isHeadline();

    @Override
    String getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
