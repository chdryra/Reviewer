package com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Implementation;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Factories.FactoryRowConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.PersistenceAndroidSqLite.Interfaces.RowConverter;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.FactoryDbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.Implementation.TableRowList;
import com.chdryra.android.reviewer.PlugIns.Persistence.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 12/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class TableTransactorSqlLite implements TableTransactor {
    private final SQLiteDatabase mDb;
    private final FactoryDbTableRow mRowFactory;
    private final FactoryRowConverter mConverterfactory;

    public TableTransactorSqlLite(SQLiteDatabase db,
                                  FactoryDbTableRow rowFactory,
                                  FactoryRowConverter converterFactory) {
        mDb = db;
        mRowFactory = rowFactory;
        mConverterfactory = converterFactory;
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
    public <T extends DbTableRow> T getRowWhere(DbTable<T> table, String col, String val) {
        Cursor cursor = getCursorWhere(table.getName(), col, val);

        if (cursor == null || cursor.getCount() == 0) return toDbTableRow(table);

        cursor.moveToFirst();
        T row = toDbTableRow(table, cursor);
        cursor.close();

        return row;
    }

    private <T extends DbTableRow> T toDbTableRow(DbTable<T> table) {
        return mRowFactory.emptyRow(table.getRowClass());
    }

    @Override
    public <T extends DbTableRow> TableRowList<T> getRowsWhere(DbTable<T> table,
                                                               @Nullable String col,
                                                               @Nullable String val) {
        Cursor cursor = getFromTableWhere(table.getName(), col, val);

        TableRowList<T> list = new TableRowList<>();
        while (cursor.moveToNext()) {
            list.add(toDbTableRow(table, cursor));
        }
        cursor.close();

        return list;
    }

    private <T extends DbTableRow> T toDbTableRow(DbTable<T> table, Cursor cursor) {
        return mRowFactory.newRow(new CursorRowValues(cursor), table.getRowClass());
    }

    @Override
    public <T extends DbTableRow> boolean insertRow(T row, DbTable<T> table) {
        DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        if (isIdInTable(id, idCol, table)) return false;

        String tableName = table.getName();
        try {
            mDb.insertOrThrow(tableName, null, convertRow(row, table));
            return true;
        } catch (SQLException e) {
            String message = id + " into " + tableName + " table ";
            throw new RuntimeException("Couldn't insert " + message, e);
        }
    }

    @Override
    public <T extends DbTableRow> void insertOrReplaceRow(T row, DbTable<T> table) {
        DbColumnDef idCol = table.getColumn(row.getRowIdColumnName());
        String id = row.getRowId();
        String tableName = table.getName();
        if (isIdInTable(id, idCol, table)) {
            try {
                mDb.replaceOrThrow(tableName, null, convertRow(row, table));
            } catch (SQLException e) {
                String message = id + " in " + tableName + " table ";
                throw new RuntimeException("Couldn't replace " + message, e);
            }
        } else {
            insertRow(row, table);
        }
    }

    private <T extends DbTableRow> ContentValues convertRow(T row, DbTable<T> table) {
        RowConverter<T> converter = mConverterfactory.newConverter(table.getRowClass());
        return converter.convert(row);
    }

    @Override
    public void deleteRows(String col, String val, DbTable table) {
        String tableName = table.getName();
        try {
            mDb.delete(tableName, col + SQL.BIND_STRING, new String[]{val});
        } catch (SQLException e) {
            String message = val + " from " + tableName + " table ";
            throw new RuntimeException("Couldn't delete " + message, e);
        }
    }

    @Override
    public boolean isIdInTable(String id, DbColumnDef idCol, DbTable table) {
        String pkCol = idCol.getName();
        Cursor cursor = getCursorWhere(table.getName(), pkCol, id);

        boolean hasRow = false;
        if (cursor != null) {
            if (cursor.moveToFirst()) hasRow = true;
            cursor.close();
        }

        return hasRow;
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
