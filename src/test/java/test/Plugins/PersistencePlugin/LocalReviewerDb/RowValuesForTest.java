/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.LocalReviewerDb;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.RowValues;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin
        .PersistenceSQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ColumnInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 21/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
class RowValuesForTest implements RowValues {
    private Map<ColumnInfo<?>, Object> mValues = new HashMap<>();


    void put(ColumnInfo<?> col, @Nullable Object value) {
        mValues.put(col, value);
    }

    @Override
    public <T> T getValue(String columnName, DbEntryType<T> entryType) {
        ColumnInfo<T> col = new ColumnInfo<>(columnName, entryType);
        T cast = null;
        try {
            Object obj = mValues.get(col);
            cast = entryType.cast(obj);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
        return cast;
    }
}
