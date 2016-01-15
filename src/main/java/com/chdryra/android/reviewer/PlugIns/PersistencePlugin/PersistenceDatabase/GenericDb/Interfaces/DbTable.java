/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 30 March, 2015
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.GenericDb.Interfaces;

import android.provider.BaseColumns;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 30/03/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DbTable<T extends DbTableRow> extends BaseColumns {
    String getName();

    Class<T> getRowClass();

    ArrayList<DbColumnDefinition> getPrimaryKeys();

    ArrayList<ForeignKeyConstraint<? extends DbTableRow>> getForeignKeyConstraints();

    ArrayList<DbColumnDefinition> getColumns();

    ArrayList<String> getColumnNames();

    void addColumn(DbColumnDefinition column);

    void addPrimaryKeyColumn(DbColumnDefinition column);

    void addForeignKeyConstraint(ForeignKeyConstraint<? extends DbTableRow> constraint);

    DbColumnDefinition getColumn(String name);
}
