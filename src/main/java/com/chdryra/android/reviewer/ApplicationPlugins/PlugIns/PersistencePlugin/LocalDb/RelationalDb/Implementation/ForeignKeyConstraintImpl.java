/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Implementation;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ForeignKeyConstraintImpl<T extends DbTableRow> implements ForeignKeyConstraint<T>{
    private ArrayList<DbColumnDefinition> mFkColumns;
    private DbTable<T> mPkTable;

    public ForeignKeyConstraintImpl(ArrayList<DbColumnDefinition> fkColumns, DbTable<T> pkTable) {
        if (fkColumns.size() != pkTable.getPrimaryKeys().size()) {
            throw new IllegalArgumentException("Number of column names should match number of " +
                    "primary key columns in pkTable!");
        }
        mFkColumns = fkColumns;
        mPkTable = pkTable;
    }

    @Override
    public ArrayList<DbColumnDefinition> getFkColumns() {
        return mFkColumns;
    }

    @Override
    public DbTable<T> getForeignTable() {
        return mPkTable;
    }
}
