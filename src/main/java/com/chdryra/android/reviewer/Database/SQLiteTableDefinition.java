/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class SQLiteTableDefinition implements BaseColumns {
    private String                          mTableName;
    private ArrayList<SQLiteColumn>         mPrimaryKeys;
    private ArrayList<SQLiteColumn>         mOtherColumns;
    private ArrayList<ForeignKeyConstraint> mFkConstraints;

    public SQLiteTableDefinition(String tableName) {
        mTableName = tableName;
        mPrimaryKeys = new ArrayList<>();
        mOtherColumns = new ArrayList<>();
        mFkConstraints = new ArrayList<>();
    }

    public void addColumn(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
        mOtherColumns.add(new SQLiteColumn(columnName, type, nullable));
    }

    public void addPrimaryKey(String columnName, SQL.StorageType type) {
        mPrimaryKeys.add(new SQLiteColumn(columnName, type, SQL.Nullable.FALSE));
    }

    public void addForeignKeyConstraint(String[] columnNames, SQLiteTableDefinition pkTable) {
        if (columnNames.length != pkTable.getPrimaryKeys().size()) {
            throw new IllegalArgumentException("Number of column names should match number of " +
                    "primary key columns in pkTable!");
        }

        ArrayList<SQLiteColumn> fkColumns = new ArrayList<>(columnNames.length);
        for (String name : columnNames) {
            SQLiteColumn fkColumn = getColumn(name);
            if (fkColumn == null) {
                throw new IllegalArgumentException("Column: " + name + " not found!");
            } else {
                fkColumns.add(fkColumn);
            }
        }

        mFkConstraints.add(new ForeignKeyConstraint(fkColumns, pkTable));
    }

    public String getName() {
        return mTableName;
    }

    public SQLiteColumn getColumn(String name) {
        for (SQLiteColumn column : getAllColumns()) {
            if (column.getName().equals(name)) return column;
        }

        return null;
    }

    public ArrayList<SQLiteColumn> getPrimaryKeys() {
        return mPrimaryKeys;
    }

    public ArrayList<ForeignKeyConstraint> getForeignKeyConstraints() {
        return mFkConstraints;
    }

    public ArrayList<SQLiteColumn> getAllColumns() {
        ArrayList<SQLiteColumn> columns = new ArrayList<>();
        columns.addAll(mPrimaryKeys);
        columns.addAll(mOtherColumns);
        return columns;
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<SQLiteColumn> columns = getAllColumns();
        ArrayList<String> columnNames = new ArrayList<>(columns.size());
        for (SQLiteColumn column : columns) {
            columnNames.add(column.getName());
        }

        return columnNames;
    }

    public class ForeignKeyConstraint {
        private ArrayList<SQLiteColumn> mFkColumns;
        private SQLiteTableDefinition   mPkTable;

        private ForeignKeyConstraint(ArrayList<SQLiteColumn> fkColumns,
                SQLiteTableDefinition pkTable) {
            mFkColumns = fkColumns;
            mPkTable = pkTable;
        }

        public ArrayList<SQLiteColumn> getFkColumns() {
            return mFkColumns;
        }

        public SQLiteTableDefinition getForeignTable() {
            return mPkTable;
        }
    }

    public class SQLiteColumn {
        private String          mColumnName;
        private SQL.StorageType mType;
        private boolean         mIsNullable;

        private SQLiteColumn(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
            mColumnName = columnName;
            mType = type;
            mIsNullable = nullable == SQL.Nullable.TRUE;
        }

        public String getName() {
            return mColumnName;
        }

        public SQL.StorageType getType() {
            return mType;
        }

        public boolean isNullable() {
            return mIsNullable;
        }

        public SQLiteTableDefinition getParentTable() {
            return SQLiteTableDefinition.this;
        }
    }
}
