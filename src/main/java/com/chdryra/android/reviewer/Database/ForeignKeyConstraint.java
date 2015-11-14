package com.chdryra.android.reviewer.Database;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ForeignKeyConstraint<T extends DbTableRow> {
    private ArrayList<DbColumnDef> mFkColumns;
    private DbTable<T> mPkTable;

    ForeignKeyConstraint(ArrayList<DbColumnDef> fkColumns,
                         DbTable<T> pkTable) {
        mFkColumns = fkColumns;
        mPkTable = pkTable;
    }

    //public methods
    public ArrayList<DbColumnDef> getFkColumns() {
        return mFkColumns;
    }

    public DbTable<T> getForeignTable() {
        return mPkTable;
    }
}
