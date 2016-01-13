package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbColumnNotNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbColumnNullable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.RowValueType;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbColumnDef {
    public DbColumnDefinition newNullableColumn(String columnName, RowValueType type) {
        return new DbColumnNullable(columnName, type);
    }

    public DbColumnDefinition newNotNullableColumn(String columnName, RowValueType type) {
        return new DbColumnNotNullable(columnName, type);
    }

    public DbColumnDefinition newPkColumn(String columnName, RowValueType type) {
        return newNotNullableColumn(columnName, type);
    }
}
