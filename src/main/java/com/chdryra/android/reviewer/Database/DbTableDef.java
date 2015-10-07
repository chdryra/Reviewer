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
public class DbTableDef implements BaseColumns {
    private String mTableName;
    private ArrayList<DbColumnDef> mPrimaryKeys;
    private ArrayList<DbColumnDef> mOtherColumns;
    private ArrayList<ForeignKeyConstraint> mFkConstraints;

    //Constructors
    public DbTableDef(String tableName) {
        mTableName = tableName;
        mPrimaryKeys = new ArrayList<>();
        mOtherColumns = new ArrayList<>();
        mFkConstraints = new ArrayList<>();
    }

    //public methods
    public String getName() {
        return mTableName;
    }

    public ArrayList<DbColumnDef> getPrimaryKeys() {
        return mPrimaryKeys;
    }

    public ArrayList<ForeignKeyConstraint> getForeignKeyConstraints() {
        return mFkConstraints;
    }

    public ArrayList<DbColumnDef> getAllColumns() {
        ArrayList<DbColumnDef> columns = new ArrayList<>();
        columns.addAll(mPrimaryKeys);
        columns.addAll(mOtherColumns);
        return columns;
    }

    public ArrayList<String> getColumnNames() {
        ArrayList<DbColumnDef> columns = getAllColumns();
        ArrayList<String> columnNames = new ArrayList<>(columns.size());
        for (DbColumnDef column : columns) {
            columnNames.add(column.getName());
        }

        return columnNames;
    }

    public void addColumn(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
        mOtherColumns.add(new DbColumnDef(columnName, type, nullable));
    }

    public void addPrimaryKey(String columnName, SQL.StorageType type) {
        mPrimaryKeys.add(new DbColumnDef(columnName, type, SQL.Nullable.FALSE));
    }

    public void addForeignKeyConstraint(String[] columnNames, DbTableDef pkTable) {
        if (columnNames.length != pkTable.getPrimaryKeys().size()) {
            throw new IllegalArgumentException("Number of column names should match number of " +
                    "primary key columns in pkTable!");
        }

        ArrayList<DbColumnDef> fkColumns = new ArrayList<>(columnNames.length);
        for (String name : columnNames) {
            DbColumnDef fkColumn = getColumn(name);
            if (fkColumn == null) {
                throw new IllegalArgumentException("Column: " + name + " not found!");
            } else {
                fkColumns.add(fkColumn);
            }
        }

        mFkConstraints.add(new ForeignKeyConstraint(fkColumns, pkTable));
    }

    public DbColumnDef getColumn(String name) {
        for (DbColumnDef column : getAllColumns()) {
            if (column.getName().equals(name)) return column;
        }

        return null;
    }

    public class ForeignKeyConstraint {
        private ArrayList<DbColumnDef> mFkColumns;
        private DbTableDef mPkTable;

        private ForeignKeyConstraint(ArrayList<DbColumnDef> fkColumns,
                                     DbTableDef pkTable) {
            mFkColumns = fkColumns;
            mPkTable = pkTable;
        }

        //public methods
        public ArrayList<DbColumnDef> getFkColumns() {
            return mFkColumns;
        }

        public DbTableDef getForeignTable() {
            return mPkTable;
        }
    }

    public class DbColumnDef {
        private String mColumnName;
        private SQL.StorageType mType;
        private boolean mIsNullable;

        private DbColumnDef(String columnName, SQL.StorageType type, SQL.Nullable nullable) {
            mColumnName = columnName;
            mType = type;
            mIsNullable = nullable == SQL.Nullable.TRUE;
        }

        //public methods
        public String getName() {
            return mColumnName;
        }

        public SQL.StorageType getType() {
            return mType;
        }

        public boolean isNullable() {
            return mIsNullable;
        }

        public DbTableDef getParentTable() {
            return DbTableDef.this;
        }
    }
}
