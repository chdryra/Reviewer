package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbEntryType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ColumnInfo)) return false;

        ColumnInfo<?> that = (ColumnInfo<?>) o;

        if (mName != null ? !mName.equals(that.mName) : that.mName != null) return false;
        return !(mType != null ? !mType.equals(that.mType) : that.mType != null);

    }

    @Override
    public int hashCode() {
        int result = mName != null ? mName.hashCode() : 0;
        result = 31 * result + (mType != null ? mType.hashCode() : 0);
        return result;
    }
}
