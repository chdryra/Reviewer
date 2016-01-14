package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Factories;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbColumnNotNullable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Implementation.DbColumnNullable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.RowValueType;

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
