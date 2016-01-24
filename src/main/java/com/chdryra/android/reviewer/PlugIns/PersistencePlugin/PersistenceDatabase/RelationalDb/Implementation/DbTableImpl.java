/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DbTableImpl<T extends DbTableRow> implements DbTable<T> {
    private String mTableName;
    private Class<T> mRowClass;
    private ArrayList<DbColumnDefinition> mPrimaryKeys;
    private ArrayList<DbColumnDefinition> mOtherColumns;
    private ArrayList<ForeignKeyConstraint<? extends DbTableRow>> mFkConstraints;

    public DbTableImpl(String tableName, Class<T> rowClass) {
        mTableName = tableName;
        mRowClass = rowClass;
        mPrimaryKeys = new ArrayList<>();
        mOtherColumns = new ArrayList<>();
        mFkConstraints = new ArrayList<>();
    }

    protected void addColumn(DbColumnDefinition column) {
        mOtherColumns.add(column);
    }

    protected void addPrimaryKeyColumn(DbColumnDefinition column) {
        if (column.getNullable().isNullable()) {
            throw new IllegalArgumentException("Pk column cannot be nullable!");
        }
        mPrimaryKeys.add(column);
    }

    protected void addForeignKeyConstraint(ForeignKeyConstraint<? extends DbTableRow> constraint) {
        checkFkConstraintsAreValid(constraint);
        mFkConstraints.add(constraint);
    }

    @Override
    public String getName() {
        return mTableName;
    }

    @Override
    public Class<T> getRowClass() {
        return mRowClass;
    }

    @Override
    public ArrayList<DbColumnDefinition> getPrimaryKeys() {
        return mPrimaryKeys;
    }

    @Override
    public ArrayList<ForeignKeyConstraint<? extends DbTableRow>> getForeignKeyConstraints() {
        return mFkConstraints;
    }

    @Override
    public ArrayList<DbColumnDefinition> getColumns() {
        ArrayList<DbColumnDefinition> columns = new ArrayList<>();
        columns.addAll(mPrimaryKeys);
        columns.addAll(mOtherColumns);
        return columns;
    }

    @Override
    public ArrayList<String> getColumnNames() {
        ArrayList<DbColumnDefinition> columns = getColumns();
        ArrayList<String> columnNames = new ArrayList<>(columns.size());
        for (DbColumnDefinition column : columns) {
            columnNames.add(column.getName());
        }

        return columnNames;
    }

    @Override
    @Nullable
    public DbColumnDefinition getColumn(String name) {
        for (DbColumnDefinition column : getColumns()) {
            if (column.getName().equals(name)) return column;
        }

        return null;
    }

    private void checkFkConstraintsAreValid(ForeignKeyConstraint<? extends DbTableRow> constraint) {
        for (DbColumnDefinition col : constraint.getFkColumns()) {
            String name = col.getName();
            if (getColumn(name) == null) {
                throw new IllegalArgumentException("Column: " + name + " not found!");
            }
        }
    }
}
