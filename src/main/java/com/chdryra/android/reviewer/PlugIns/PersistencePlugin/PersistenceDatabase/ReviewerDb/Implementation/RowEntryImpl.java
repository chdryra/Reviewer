package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowEntryImpl<T> implements RowEntry<T> {
    private ColumnInfo<T> mInfo;
    private T mValue;

    public RowEntryImpl(ColumnInfo<T> info, T value) {
        mInfo = info;
        mValue = value;
    }

    @Override
    public String getColumnName() {
        return mInfo.getName();
    }

    @Override
    public DbEntryType<T> getEntryType() {
        return mInfo.getType();
    }

    @Override
    public T getValue() {
        return mValue;
    }
}
