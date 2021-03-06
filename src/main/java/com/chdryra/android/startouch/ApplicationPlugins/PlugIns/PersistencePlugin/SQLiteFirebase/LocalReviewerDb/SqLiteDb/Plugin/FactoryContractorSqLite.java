/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Plugin;


import android.content.Context;

import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.ContractorDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.FactoryContractor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbContract;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbSpecification;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.ContractorSqLite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.EntryToStringConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.FactoryTransactorSqLite;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.RowToValuesConverter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.SqLiteTypeDefinitions;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .LocalReviewerDb.SqLiteDb.Implementation.TablesSql;

/**
 * Created by: Rizwan Choudrey
 * On: 14/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryContractorSqLite implements FactoryContractor {
    private final FactoryTransactorSqLite mTransactorFactory;
    private final TablesSql mTableMaker;

    public FactoryContractorSqLite() {
        mTransactorFactory = new FactoryTransactorSqLite(new RowToValuesConverter(), new
                EntryToStringConverter());
        mTableMaker = new TablesSql(new SqLiteTypeDefinitions());
    }

    @Override
    public <T extends DbContract> ContractorDb<T> newContractor(Context context,
                                                                DbSpecification<T> spec) {
        return new ContractorSqLite<>(context, spec, mTableMaker, mTransactorFactory);
    }
}
