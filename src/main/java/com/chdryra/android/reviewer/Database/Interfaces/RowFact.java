package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowFact extends ReviewDataRow, DataFact {
    String COLUMN_FACT_ID = "fact_id";
    String COLUMN_REVIEW_ID = "review_id";
    String COLUMN_LABEL = "label";
    String COLUMN_VALUE = "value";
    String COLUMN_IS_URL = "is_url";
    
    @Override
    String getLabel();

    @Override
    String getValue();

    @Override
    boolean isUrl();

    @Override
    ReviewId getReviewId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
