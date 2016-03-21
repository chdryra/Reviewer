/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDbPlugin
        .Api;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.DbTableRow;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.RelationalDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb
        .ReviewerDb.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface TableTransactor {
    void beginTransaction();

    void endTransaction();

    <Row extends DbTableRow> TableRowList<Row> loadTable(DbTable<Row> table,
                                                         FactoryDbTableRow rowFactory);

    <Row extends DbTableRow, Type> TableRowList<Row> getRowsWhere(DbTable<Row> table,
                                                                  RowEntry<Row, Type> clause,
                                                                  FactoryDbTableRow rowFactory);

    <Row extends DbTableRow, Type> int deleteRowsWhere(DbTable<Row> table,
                                                        RowEntry<Row, Type> clause);

    <Row extends DbTableRow> boolean insertRow(Row row, DbTable<Row> table);

    <Row extends DbTableRow> boolean insertOrReplaceRow(Row row, DbTable<Row> table);

    boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table);
}
