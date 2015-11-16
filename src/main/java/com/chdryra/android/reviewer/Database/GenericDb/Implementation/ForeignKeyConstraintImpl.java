package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ForeignKeyConstraintImpl<T extends DbTableRow> implements ForeignKeyConstraint<T>{
    private ArrayList<DbColumnDef> mFkColumns;
    private DbTable<T> mPkTable;

    public ForeignKeyConstraintImpl(ArrayList<DbColumnDef> fkColumns,
                             DbTable<T> pkTable) {
        mFkColumns = fkColumns;
        mPkTable = pkTable;
    }

    @Override
    public ArrayList<DbColumnDef> getFkColumns() {
        return mFkColumns;
    }

    @Override
    public DbTable<T> getForeignTable() {
        return mPkTable;
    }
}
