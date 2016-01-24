/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb.Interfaces.DbTableRow;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;
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

    <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table, FactoryDbTableRow rowFactory);

    <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                      RowEntry<Type> entry,
                                                                      FactoryDbTableRow rowFactory);

    <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table);

    <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table);

    <Type> void deleteRows(DbTable<?> table, RowEntry<Type> entry);

    boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table);
}
