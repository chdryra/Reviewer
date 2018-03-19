/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.Implementation;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbColumnDef;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbTableImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 15/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
class ReviewerDbTableImpl<T extends DbTableRow> extends DbTableImpl<T> {
    private final FactoryDbColumnDef mColumnFactory;

    ReviewerDbTableImpl(String tableName, Class<T> rowClass, FactoryDbColumnDef columnFactory) {
        super(tableName, rowClass);
        mColumnFactory = columnFactory;
    }

    protected <Type> void addNullableColumn(ColumnInfo<Type> info) {
        addColumn(mColumnFactory.newNullableColumn(info.getName(), info.getType()));
    }

    <Type> void addPkColumn(ColumnInfo<Type> info) {
        addPrimaryKeyColumn(mColumnFactory.newPkColumn(info.getName(), info.getType()));
    }

    <Type> void addNotNullableColumn(ColumnInfo<Type> info) {
        addColumn(mColumnFactory.newNotNullableColumn(info.getName(), info.getType()));
    }
}
