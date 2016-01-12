package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.StorageType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class StorageTypeImpl implements StorageType {
    private String mType;

    public StorageTypeImpl(String type) {
        mType = type;
    }

    @Override
    public String getTypeString() {
        return mType;
    }
}
