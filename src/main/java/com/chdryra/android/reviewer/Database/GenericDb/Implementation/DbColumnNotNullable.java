package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbColumnNotNullable extends DbColumnDefinitionImpl {
    public DbColumnNotNullable(String columnName, RowValueType type) {
        super(columnName, type);
    }

    @Override
    public ValueNullable getNullable() {
        return ValueNullable.TRUE;
    }
}