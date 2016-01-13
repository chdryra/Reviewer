package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbColumnDefinitionBasic implements DbColumnDefinition {
    private String mColumnName;
    private RowValueType mType;

    public DbColumnDefinitionBasic(String columnName, RowValueType type) {
        mColumnName = columnName;
        mType = type;
    }

    @Override
    public String getName() {
        return mColumnName;
    }

    @Override
    public RowValueType getType() {
        return mType;
    }
}
