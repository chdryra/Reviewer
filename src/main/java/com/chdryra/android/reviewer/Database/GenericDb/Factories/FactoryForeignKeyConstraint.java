package com.chdryra.android.reviewer.Database.GenericDb.Factories;

import com.chdryra.android.reviewer.Database.GenericDb.Implementation.ForeignKeyConstraintImpl;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryForeignKeyConstraint {
    public <T extends DbTableRow> ForeignKeyConstraint<T> newConstraint(ArrayList<DbColumnDef> fkColumns,
                                                                      DbTable<T> pkTable) {
        return new ForeignKeyConstraintImpl<>(fkColumns, pkTable);

    }
}
