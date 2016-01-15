package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.DbEntryType;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowEntryImpl<T> implements RowEntry<T> {
    private String mColumName;
    private DbEntryType mType;
    private T mValue;

    public RowEntryImpl(String columName, DbEntryType type, T value) {
        mColumName = columName;
        mType = type;
        mValue = value;
    }

    @Override
    public String getColumnName() {
        return mColumName;
    }

    @Override
    public DbEntryType getEntryType() {
        return mType;
    }

    @Override
    public T getValue() {
        return mValue;
    }
}
