package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.DatabaseAndroidSqLite.Interfaces;

import android.content.ContentValues;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.GenericDb.Interfaces.DbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface RowConverter<T extends DbTableRow> {
    ContentValues convert(T row);
}
