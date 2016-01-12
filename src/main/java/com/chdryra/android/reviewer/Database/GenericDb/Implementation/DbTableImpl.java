/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.Database.GenericDb.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbColumnDef;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.Database.GenericDb.Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbTableImpl<T extends DbTableRow> implements DbTable<T>{
    private String mTableName;
    private Class<? extends T> mRowClass;
    private ArrayList<DbColumnDef> mPrimaryKeys;
    private ArrayList<DbColumnDef> mOtherColumns;
    private ArrayList<ForeignKeyConstraint<? extends DbTableRow>> mFkConstraints;

    public DbTableImpl(String tableName, Class<? extends T> rowClass) {
        mTableName = tableName;
        mRowClass = rowClass;
        mPrimaryKeys = new ArrayList<>();
        mOtherColumns = new ArrayList<>();
        mFkConstraints = new ArrayList<>();
    }

    @Override
    public String getName() {
        return mTableName;
    }

    @Override
    public Class<? extends T> getRowClass() {
        return mRowClass;
    }

    @Override
    public ArrayList<DbColumnDef> getPrimaryKeys() {
        return mPrimaryKeys;
    }

    @Override
    public ArrayList<ForeignKeyConstraint<? extends DbTableRow>> getForeignKeyConstraints() {
        return mFkConstraints;
    }

    @Override
    public ArrayList<DbColumnDef> getColumns() {
        ArrayList<DbColumnDef> columns = new ArrayList<>();
        columns.addAll(mPrimaryKeys);
        columns.addAll(mOtherColumns);
        return columns;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        ArrayList<DbColumnDef> columns = getColumns();
        ArrayList<String> columnNames = new ArrayList<>(columns.size());
        for (DbColumnDef column : columns) {
            columnNames.add(column.getName());
        }

        return columnNames;
    }

    @Override
    public void addColumn(DbColumnDef column) {
        mOtherColumns.add(column);
    }

    @Override
    public void addPrimaryKey(DbColumnDef column) {
        if(column.getNullable()) {
            throw new IllegalArgumentException("Pk column cannot be nullable!");
        }
        mPrimaryKeys.add(column);
    }

    @Override
    public void addForeignKeyConstraint(ForeignKeyConstraint<? extends DbTableRow> constraint) {
        ArrayList<DbColumnDef> columns = constraint.getFkColumns();
        DbTable<? extends DbTableRow> pkTable = constraint.getForeignTable();
        if (columns.size() != pkTable.getPrimaryKeys().size()) {
            throw new IllegalArgumentException("Number of column names should match number of " +
                    "primary key columns in pkTable!");
        }

        for (DbColumnDef col : columns) {
            String name = col.getName();
            DbColumnDef fkColumn = getColumn(name);
            if (fkColumn == null) {
                throw new IllegalArgumentException("Column: " + name + " not found!");
            }
        }

        mFkConstraints.add(constraint);
    }

    @Override
    @Nullable
    public DbColumnDef getColumn(String name) {
        for (DbColumnDef column : getColumns()) {
            if (column.getName().equals(name)) return column;
        }

        return null;
    }

}
