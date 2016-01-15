package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Implementation.DbEntryType;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ColumnInfo<T> {
    private String mName;
    private DbEntryType<T> mType;

    public ColumnInfo(String name, DbEntryType<T> type) {
        mName = name;
        mType = type;
    }

    public String getName() {
        return mName;
    }

    public DbEntryType<T> getType() {
        return mType;
    }
}
