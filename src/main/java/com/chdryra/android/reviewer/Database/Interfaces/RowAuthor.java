package com.chdryra.android.reviewer.Database.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 09/04/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowAuthor extends DbTableRow, DataAuthor {
    String COLUMN_USER_ID = "user_id";
    String COLUMN_AUTHOR_NAME = "name";

    @Override
    String getName();

    @Override
    String getUserId();

    @Override
    String getRowId();

    @Override
    String getRowIdColumnName();

    @Override
    ContentValues getContentValues();

    @Override
    boolean hasData(DataValidator validator);
}