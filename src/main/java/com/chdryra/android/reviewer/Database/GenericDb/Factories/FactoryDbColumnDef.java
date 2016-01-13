package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbColumnDefImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ValueNullable;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbColumnDef {
    public DbColumnDef newColumnDef(String columnName, RowValueType type, ValueNullable nullable) {
        return new DbColumnDefImpl(columnName, type, nullable);
    }

    public DbColumnDef newPkColumnDef(String columnName, RowValueType type) {
        return new DbColumnDefImpl(columnName, type, ValueNullable.FALSE);
    }
}
