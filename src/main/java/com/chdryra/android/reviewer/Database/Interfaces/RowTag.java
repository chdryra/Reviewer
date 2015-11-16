package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowTag extends DbTableRow {
    String COLUMN_TAG = "tag";
    String COLUMN_REVIEWS = "reviews";

    String getTag();

    ArrayList<String> getReviewIds();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}
