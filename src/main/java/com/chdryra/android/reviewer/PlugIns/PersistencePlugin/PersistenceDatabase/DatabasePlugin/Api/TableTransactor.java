package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface TableTransactor {
    void beginTransaction();

    void endTransaction();

    <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table);

    <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                      RowEntry<Type> entry);

    <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table);

    <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table);

    <Type> void deleteRows(DbTable<?> table, RowEntry<Type> entry);

    boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table);
}
