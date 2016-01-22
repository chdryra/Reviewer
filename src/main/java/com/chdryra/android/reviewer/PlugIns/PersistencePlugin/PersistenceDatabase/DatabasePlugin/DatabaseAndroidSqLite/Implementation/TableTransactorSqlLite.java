package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin
        .DatabaseAndroidSqLite.Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.DatabasePlugin.Api.TableTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces.FactoryDbTableRow;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb
        .Interfaces.RowEntry;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.ReviewerDb.Implementation.TableRowList;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTransactorSqlLite implements TableTransactor {
    private final SQLiteDatabase mDb;
    private final FactoryDbTableRow mRowFactory;
    private final RowToValuesConverter mRowConverter;
    private final EntryToStringConverter mEntryConverter;

    public TableTransactorSqlLite(SQLiteDatabase db,
                                  FactoryDbTableRow rowFactory,
                                  RowToValuesConverter rowConverter,
                                  EntryToStringConverter entryConverter) {
        mDb = db;
        mRowFactory = rowFactory;
        mRowConverter = rowConverter;
        mEntryConverter = entryConverter;
    }

    @Override
    public void beginTransaction() {
        mDb.beginTransaction();
    }

    @Override
    public void endTransaction() {
        mDb.setTransactionSuccessful();
        mDb.endTransaction();
        mDb.close();
    }

    @Override
    public <DbRow extends DbTableRow, Type> TableRowList<DbRow> getRowsWhere(DbTable<DbRow> table,
                                                                             RowEntry<Type> entry) {
        return getAllRowsWhere(table, entry);
    }

    @Override
    public <DbRow extends DbTableRow> TableRowList<DbRow> loadTable(DbTable<DbRow> table) {
        return getAllRowsWhere(table, null);
    }

    @Override
    public <DbRow extends DbTableRow> boolean insertRow(DbRow row, DbTable<DbRow> table) {
        DbColumnDefinition idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        if (isIdInTable(id, idCol, table)) return false;

        String tableName = table.getName();
        try {
            mDb.insertOrThrow(tableName, null, convertRow(row));
            return true;
        } catch (SQLException e) {
            String message = id + " into " + tableName + " table ";
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    @Override
    public <DbRow extends DbTableRow> void insertOrReplaceRow(DbRow row, DbTable<DbRow> table) {
        DbColumnDefinition idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        String tableName = table.getName();
        if (isIdInTable(id, idCol, table)) {
            try {
                mDb.replaceOrThrow(tableName, null, convertRow(row));
            } catch (SQLException e) {
                String message = id + " in " + tableName + " table ";
                throw new RuntimeException("Couldn't replace " + message, e);
            }
        } else {
            insertRow(row, table);
        }
    }

    @Override
    public <Type> void deleteRows(DbTable<?> table, RowEntry<Type> entry) {
        String tableName = table.getName();
        String val = mEntryConverter.convert(entry);
        String message = "Couldn't delete " + entry.getValue() + " from " + tableName + " table ";
        if(val == null) throw new IllegalArgumentException(message);
        try {
            mDb.delete(tableName, entry.getColumnName() + SQL.BIND_STRING, new String[]{val});
        } catch (SQLException e) {
            throw new RuntimeException(message, e);
        }
    }

    @Override
    public boolean isIdInTable(String id, DbColumnDefinition idCol, DbTable<?> table) {
        String col = idCol.getName();
        Cursor cursor = getCursorWhere(table.getName(), col, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
    }

    private <DbRow extends DbTableRow> DbRow toDbTableRow(DbTable<DbRow> table) {
        return mRowFactory.emptyRow(table.getRowClass());
    }

    @NonNull
    private <DbRow extends DbTableRow, Type> TableRowList<DbRow> getAllRowsWhere(DbTable<DbRow> table,
                                                                           @Nullable RowEntry<Type> entry) {
        String col = null;
        String val = null;
        if(entry != null) {
            col = entry.getColumnName();
            val = mEntryConverter.convert(entry);
        }

        Cursor cursor = getFromTableWhere(table.getName(), col, val);
        TableRowList<DbRow> list = new TableRowList<>();
        if (cursor == null || cursor.getCount() == 0) {
            list.add(toDbTableRow(table));
            return list;
        }

        while (cursor.moveToNext()) {
            list.add(toDbTableRow(table, cursor));
        }
        cursor.close();

        return list;
    }

    private <DbRow extends DbTableRow> DbRow toDbTableRow(DbTable<DbRow> table, Cursor cursor) {
        return mRowFactory.newRow(new CursorRowValues(cursor), table.getRowClass());
    }

    private <DbRow extends DbTableRow> ContentValues convertRow(DbRow row) {
        return mRowConverter.convert(row);
    }

    private Cursor getFromTableWhere(String table, @Nullable String column, @Nullable String
            value) {
        boolean isNull = value == null;
        String val = isNull ? SQL.SPACE + SQL.IS_NULL : SQL.SPACE + SQL.BIND_STRING;
        String whereClause = column != null ? " " + SQL.WHERE + column + val : "";
        String query = SQL.SELECT + SQL.ALL + SQL.FROM + table + whereClause;
        String[] args = isNull ? null : new String[]{value};
        return mDb.rawQuery(query, args);
    }

    @Nullable
    private Cursor getCursorWhere(String table, String pkColumn, String pkValue) {
        Cursor cursor = getFromTableWhere(table, pkColumn, pkValue);
        if (cursor != null && cursor.getCount() > 1) {
            cursor.close();
            throw new IllegalStateException("Cannot have more than 1 row with same primary key!");
        }

        return cursor;
    }
}
