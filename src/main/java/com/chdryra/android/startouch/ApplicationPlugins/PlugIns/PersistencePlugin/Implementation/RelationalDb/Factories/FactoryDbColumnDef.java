/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbColumnNotNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbColumnNullable;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbEntryType;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDbColumnDef {
    public DbColumnDefinition newNullableColumn(String columnName, DbEntryType type) {
        return new DbColumnNullable(columnName, type);
    }

    public DbColumnDefinition newNotNullableColumn(String columnName, DbEntryType type) {
        return new DbColumnNotNullable(columnName, type);
    }

    public DbColumnDefinition newPkColumn(String columnName, DbEntryType type) {
        return newNotNullableColumn(columnName, type);
    }
}
