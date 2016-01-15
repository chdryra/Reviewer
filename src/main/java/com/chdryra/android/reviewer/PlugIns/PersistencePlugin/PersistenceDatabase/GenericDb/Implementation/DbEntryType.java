package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DbEntryType<T> {
    private String mTypeName;
    private Class<T> mTypeClass;

    public DbEntryType(String typeName, Class<T> typeClass) {
        mTypeName = typeName;
        mTypeClass = typeClass;
    }

    public String getTypeName() {
        return mTypeName;
    }
}
