/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Factories;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Implementation.ForeignKeyConstraintImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbColumnDefinition;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.DbTableRow;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.ForeignKeyConstraint;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryForeignKeyConstraint {
    public <T extends DbTableRow> ForeignKeyConstraint<T> newConstraint(ArrayList<DbColumnDefinition> fkColumns,
                                                                      DbTable<T> pkTable) {
        return new ForeignKeyConstraintImpl<>(fkColumns, pkTable);
    }
}
