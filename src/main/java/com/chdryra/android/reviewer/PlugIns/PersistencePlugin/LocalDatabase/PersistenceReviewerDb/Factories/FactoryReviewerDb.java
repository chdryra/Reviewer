/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Factories;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Implementation.ReviewerDbImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb.Interfaces.FactoryDbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 14/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewerDb {
    private FactoryDbTableRow mRowFactory;

    public FactoryReviewerDb(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    public ReviewerDb newDatabase(ContractorDb<ReviewerDbContract> contractor,
                                  ReviewTransactor transactor,
                                  DataValidator dataValidator) {
        return new ReviewerDbImpl(contractor, transactor, mRowFactory, dataValidator);
    }
}
