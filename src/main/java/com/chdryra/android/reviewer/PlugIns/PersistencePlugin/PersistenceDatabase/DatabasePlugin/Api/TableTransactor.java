package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface TableTransactor {
    void beginTransaction();

    void endTransaction();

    <DbRow extends DbTableRow> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                        String col,
                                                        String val);

    <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table);

    boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table);

    <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table);

    <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table);

    void deleteRows(DbTable<?> table, String col, String val);
}
