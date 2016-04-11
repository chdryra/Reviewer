/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Implementation;


import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb
        .Interfaces.DbColumnDefinition;

/**
 * Created by: Rizwan Choudrey
 * On: 14/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class DbColumnDefinitionBasic implements DbColumnDefinition {
    private String mColumnName;
    private DbEntryType<?> mType;

    public DbColumnDefinitionBasic(String columnName, DbEntryType<?> type) {
        mColumnName = columnName;
        mType = type;
    }

    @Override
    public String getName() {
        return mColumnName;
    }

    @Override
    public DbEntryType<?> getType() {
        return mType;
    }
}
