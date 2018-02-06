/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces;

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

    ArrayList<DbColumnDefinition> getColumns();

    ArrayList<String> getColumnNames();

    DbColumnDefinition getColumn(String name);

    ArrayList<ForeignKeyConstraint<? extends DbTableRow>> getForeignKeyConstraints();
}
