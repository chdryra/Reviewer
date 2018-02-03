/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories;



import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.ContractorDb;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDb {
    private final FactoryDbTableRow mRowFactory;

    public FactoryReviewerDb(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    public ReviewerDb newDatabase(ContractorDb<ReviewerDbContract> contractor,
                                  ReviewTransactor transactor,
                                  DataValidator dataValidator) {
        return new ReviewerDbImpl(contractor, transactor, mRowFactory, dataValidator);
    }
}
