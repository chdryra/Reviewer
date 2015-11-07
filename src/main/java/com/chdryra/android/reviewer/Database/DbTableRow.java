package com.chdryra.android.reviewer.Database;

import android.content.ContentValues;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbTableRow {
    //abstract
    String getRowId();

    String getRowIdColumnName();

    ContentValues getContentValues();

    boolean hasData();
}
