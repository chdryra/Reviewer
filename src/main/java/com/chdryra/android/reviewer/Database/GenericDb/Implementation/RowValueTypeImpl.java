package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class RowValueTypeImpl implements RowValueType {
    private String mType;

    public RowValueTypeImpl(String type) {
        mType = type;
    }

    @Override
    public String getTypeString() {
        return mType;
    }
}
