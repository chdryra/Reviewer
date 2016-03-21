/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin
        .AndroidSqLiteDb.Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin
        .Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase
        .PersistenceReviewerDb.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.RowEntry;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTransactorSqLite implements TableTransactor {
    private final SqLiteDb mDb;
    private final TablesSql mSql;
    private final RowToValuesConverter mRowConverter;
    private final EntryToStringConverter mEntryConverter;

    public TableTransactorSqLite(SqLiteDb db,
                                 TablesSql sql,
                                 RowToValuesConverter rowConverter,
                                 EntryToStringConverter entryConverter) {
        mDb = db;
        mSql = sql;
        mRowConverter = rowConverter;
        mEntryConverter = entryConverter;
    }

    @Override
    public void beginTransaction() {
        mDb.beginTransaction();
    }

    @Override
    public void endTransaction() {
        mDb.endTransaction();
    }

    @Override
    public <Row extends DbTableRow> TableRowList<Row> loadTable(DbTable<Row> table,
                                                                FactoryDbTableRow rowFactory) {
        return getAllRowsWhere(table, null, rowFactory);
    }

    @Override
    public <Row extends DbTableRow, Type> TableRowList<Row> getRowsWhere(DbTable<Row> table,
                                                                         RowEntry<Row, Type> clause,
                                                                         FactoryDbTableRow
                                                                                 rowFactory) {
        return getAllRowsWhere(table, clause, rowFactory);
    }

    @Override
    public <Row extends DbTableRow> boolean insertRow(Row row, DbTable<Row> table) {
        return insertOrReplace(row, table, false);
    }

    @Override
    public <Row extends DbTableRow> boolean insertOrReplaceRow(Row row, DbTable<Row> table) {
        return insertOrReplace(row, table, true);
    }

    @Override
    public <Row extends DbTableRow, Type> int deleteRowsWhere(DbTable<Row> table,
                                                               RowEntry<Row, Type> clause) {
        String val = mEntryConverter.convert(clause);
        if (val == null) throw new IllegalArgumentException("Null values not allowed");
        TablesSql.Query query = mSql.bindColumnWithValue(clause.getColumnName(), val);
        return mDb.delete(table.getName(), query.getQuery(), query.getArgs());
    }

    @Override
    public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
        Cursor cursor = getFromTableWhere(table, idCol.getName(), id);

        if (cursor == null) return false;

        try {
            if (cursor.getCount() > 1) {
                throw new IllegalStateException("Cannot have more than 1 row with same Id!");
            }

            return cursor.moveToFirst();
        } finally {
            cursor.close();
        }
    }

    private <Row extends DbTableRow> boolean insertOrReplace(Row row, DbTable<Row> table,
                                                             boolean replace) {
        String tableName = table.getName();
        ContentValues values = mRowConverter.convert(row);
        String id = row.getRowId();
        String idCol = row.getRowIdColumnName();

        boolean success = false;
        boolean idInTable = isIdInTable(id, table.getColumn(idCol), table);
        if (!idInTable) {
            success = mDb.insertOrThrow(tableName, values, id) != -1;
        } else if (replace) {
            success = mDb.replaceOrThrow(tableName, values, id) != -1;
        }

        return success;
    }

    @NonNull
    private <Row extends DbTableRow, Type> TableRowList<Row>
    getAllRowsWhere(DbTable<Row> table, @Nullable RowEntry<Row, Type> entry,
                    FactoryDbTableRow rowFactory) {
        String col = null;
        String val = null;
        if (entry != null) {
            col = entry.getColumnName();
            val = mEntryConverter.convert(entry);
        }

        Cursor cursor = getFromTableWhere(table, col, val);

        return convertToTableRowList(table.getRowClass(), rowFactory, cursor);
    }

    private <Row extends DbTableRow> Cursor getFromTableWhere(DbTable<Row> table,
                                                              @Nullable String column,
                                                              @Nullable String value) {
        TablesSql.Query query = mSql.getFromTableWhereQuery(table, column, value);
        return mDb.rawQuery(query.getQuery(), query.getArgs());
    }

    @NonNull
    private <Row extends DbTableRow> TableRowList<Row> convertToTableRowList(Class<Row> rowClass,
                                                                             FactoryDbTableRow
                                                                                     rowFactory,
                                                                             Cursor cursor) {
        TableRowList<Row> list = new TableRowList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                list.add(rowFactory.newRow(rowClass, new CursorRowValues(cursor)));
            }

            cursor.close();
        }

        return list;
    }
}
