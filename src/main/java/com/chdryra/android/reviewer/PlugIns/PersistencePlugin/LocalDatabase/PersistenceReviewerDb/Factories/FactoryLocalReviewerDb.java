/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb.Factories;


import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsRepositoryModel.ReviewerDbRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.PersistenceReviewerDb
        .Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDb
        .Interfaces.DbSpecification;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin
        .Api.ContractorDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.RelationalDbPlugin;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDatabase.RelationalDbPlugin.Api.FactoryContractor;


import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Plugin.FactoryPersistence;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLocalReviewerDb implements FactoryPersistence {
    private FactoryContractor mContractorFactory;
    private DbSpecification<ReviewerDbContract> mSpec;
    private FactoryReviewerDb mDbFactory;
    private FactoryReviewTransactor mReviewTransactor;

    public FactoryLocalReviewerDb(String name, int version, RelationalDbPlugin dbPlugin) {
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        mReviewTransactor = new FactoryReviewTransactor(rowFactory);
        mDbFactory = new FactoryReviewerDb(rowFactory);
        mSpec = newDbSpecification(name, dbPlugin.getDbNameExtension(), version);
        mContractorFactory = dbPlugin.getContractorFactory();
    }

    @Override
    public ReviewsRepositoryMutable newPersistence(Context context, ModelContext model) {
        return new ReviewerDbRepository(newReviewerDb(context, model), model.getTagsManager());
    }

    private ReviewerDb newReviewerDb(Context context, ModelContext model) {
        ReviewRecreater reviewFactory = model.getReviewsFactory();
        DataValidator dataValidator = model.getDataValidator();

        ContractorDb<ReviewerDbContract> contractor
                = mContractorFactory.newContractor(context, mSpec);
        ReviewTransactor transactor
                = mReviewTransactor.newStaticLoader(reviewFactory, dataValidator);
        return mDbFactory.newDatabase(contractor, transactor, dataValidator);
    }

    private DbSpecification<ReviewerDbContract> newDbSpecification(String name, String ext,
                                                                   int version) {
        ReviewerDbContract contract = new FactoryReviewerDbContract().newContract();
        return new FactoryDbSpecification().newSpecification(contract, name + "." + ext, version);
    }
}
