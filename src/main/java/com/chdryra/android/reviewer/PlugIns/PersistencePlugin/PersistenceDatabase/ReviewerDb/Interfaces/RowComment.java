package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowComment extends ReviewDataRow, DataComment {
    String COLUMN_COMMENT_ID = "comment_id";
    DbEntryType<String> COLUMN_COMMENT_ID_TYPE = DbEntryType.TEXT;

    String COLUMN_REVIEW_ID = "review_id";
    DbEntryType<String> COLUMN_REVIEW_ID_TYPE = DbEntryType.TEXT;

    String COLUMN_COMMENT = "comment";
    DbEntryType<String> COLUMN_COMMENT_TYPE = DbEntryType.TEXT;

    String COLUMN_IS_HEADLINE = "is_headline";
    DbEntryType<Boolean> COLUMN_IS_HEADLINE_TYPE = DbEntryType.BOOLEAN;

    @Override
    String getComment();

    @Override
    boolean isHeadline();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    boolean hasData(DataValidator validator);
}
