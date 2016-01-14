package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Interfaces;

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

    <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table,
                                                        String col,
                                                        String val);

    <T extends DbTableRow> TableRowList<T> loadTable(DbTable<T> table);

    boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable table);

    <T extends DbTableRow> boolean insertRow(T row, DbTable<T> table);

    <T extends DbTableRow> void insertOrReplaceRow(T row, DbTable<T> table);

    void deleteRows(String col, String val, DbTable table);
}
