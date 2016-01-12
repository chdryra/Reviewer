package com.chdryra.android.reviewer.Database.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DatabaseInstance {
    void beginTransaction();

    void endTransaction();

    <T extends DbTableRow> T getRowWhere(DbTable<T> table, String col, String val);

    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table,
                                                        @Nullable String col,
                                                        @Nullable String val);

    boolean isIdInTable(String id, DbColumnDef idCol, DbTable table);

    boolean insertRow(DbTableRow row, DbTable table);

    void insertOrReplaceRow(DbTableRow row, DbTable table);

    void deleteRows(String col, String val, DbTable table);
}
