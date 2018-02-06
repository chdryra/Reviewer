/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package test.Plugins.PersistencePlugin.RelationalDb;


import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Implementation.DbTableImpl;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.ForeignKeyConstraint;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */ //to test protected methods
class DbTableForTesting<T extends DbTableRow> extends DbTableImpl<T> {
    public DbTableForTesting(String tableName, Class<T> rowClass) {
        super(tableName, rowClass);
    }

    @Override
    public void addColumn(DbColumnDefinition column) {
        super.addColumn(column);
    }

    @Override
    public void addPrimaryKeyColumn(DbColumnDefinition column) {
        super.addPrimaryKeyColumn(column);
    }

    @Override
    public void addForeignKeyConstraint(ForeignKeyConstraint<? extends DbTableRow>
                                                    constraint) {
        super.addForeignKeyConstraint(constraint);
    }
}
