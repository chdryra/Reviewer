package com.chdryra.android.reviewer.Database.GenericDb.Interfaces;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ForeignKeyConstraint<T extends DbTableRow> {
    ArrayList<DbColumnDef> getFkColumns();

    DbTable<T> getForeignTable();
}
