package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.DbColumnDefImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Implementation.SQL;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbColumnDef {
    public DbColumnDef newColumnDef(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
        return new DbColumnDefImpl(columnName, type, nullable);
    }

    public DbColumnDef newPkColumnDef(String columnName, SQL.StorageType type) {
        return new DbColumnDefImpl(columnName, type, SQL.Nullable.FALSE);
    }
}
