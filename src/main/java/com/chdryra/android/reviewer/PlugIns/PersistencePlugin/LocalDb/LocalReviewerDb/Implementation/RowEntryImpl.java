/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;



import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.RowEntry;


/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowEntryImpl<DbRow extends DbTableRow, Type> implements RowEntry<DbRow, Type> {
    private Class<DbRow> mRowClass;
    private ColumnInfo<Type> mInfo;
    private Type mValue;

    public RowEntryImpl(Class<DbRow> rowClass, ColumnInfo<Type> info, @Nullable Type value) {
        mRowClass = rowClass;
        mInfo = info;
        mValue = value;
    }

    @Override
    public Class<DbRow> getRowClass() {
        return mRowClass;
    }

    @Override
    public String getColumnName() {
        return mInfo.getName();
    }

    @Override
    public DbEntryType<Type> getEntryType() {
        return mInfo.getType();
    }

    @Override
    public Type getValue() {
        return mValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RowEntryImpl)) return false;

        RowEntryImpl<?, ?> rowEntry = (RowEntryImpl<?, ?>) o;

        if (mRowClass != null ? !mRowClass.equals(rowEntry.mRowClass) : rowEntry.mRowClass != null)
            return false;
        if (mInfo != null ? !mInfo.equals(rowEntry.mInfo) : rowEntry.mInfo != null) return false;
        return !(mValue != null ? !mValue.equals(rowEntry.mValue) : rowEntry.mValue != null);

    }

    @Override
    public int hashCode() {
        int result = mRowClass != null ? mRowClass.hashCode() : 0;
        result = 31 * result + (mInfo != null ? mInfo.hashCode() : 0);
        result = 31 * result + (mValue != null ? mValue.hashCode() : 0);
        return result;
    }
}
