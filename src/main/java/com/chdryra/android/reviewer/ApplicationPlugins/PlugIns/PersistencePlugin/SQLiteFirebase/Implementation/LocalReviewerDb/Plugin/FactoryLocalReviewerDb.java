/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Plugin;



import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Api
        .FactoryPersistence;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.ContractorDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.FactoryContractor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Api.RelationalDbPlugin;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Factories.FactoryDbSpecification;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .RelationalDb.Interfaces.DbSpecification;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryReviewReference;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryReviewTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryReviewerDbContract;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Factories.FactoryReviewerDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.reviewer.Persistence.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLocalReviewerDb implements FactoryPersistence {
    private static final FactoryReviewReference REFERENCE_FACTORY = new FactoryReviewReference();

    private FactoryContractor mContractorFactory;
    private String mExt;
    private FactoryReviewerDb mDbFactory;
    private FactoryReviewTransactor mReviewTransactor;
    private String mPersistenceName;
    private int mPersistenceVer;
    private Context mContext;

    public FactoryLocalReviewerDb(Context context, RelationalDbPlugin dbPlugin, String name, int version) {
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        mDbFactory = new FactoryReviewerDb(rowFactory);
        mReviewTransactor = new FactoryReviewTransactor(rowFactory);
        mContractorFactory = dbPlugin.getContractorFactory();
        mPersistenceName = name;
        mPersistenceVer = version;
        mExt = dbPlugin.getDbNameExtension();
        mContext = context;
    }

    @Override
    public ReviewsRepositoryMutable newPersistence(ModelContext model, DataValidator validator, FactoryReviewsRepository repoFactory) {
        ReviewerDb db = newReviewerDb(mPersistenceName, mPersistenceVer, model.getReviewsFactory(), validator);
        return new ReviewerDbRepository(db, model.getTagsManager(), repoFactory, REFERENCE_FACTORY);
    }

    public ReviewerDb newReviewerDb(String name, int version, ReviewMaker recreater, DataValidator validator) {
        DbSpecification<ReviewerDbContract> spec = newDbSpecification(name, mExt, version);

        ContractorDb<ReviewerDbContract> contractor
                = mContractorFactory.newContractor(mContext, spec);
        ReviewTransactor transactor
                = mReviewTransactor.newStaticLoader(recreater, validator);

        return mDbFactory.newDatabase(contractor, transactor, validator);
    }

    private DbSpecification<ReviewerDbContract> newDbSpecification(String name, String ext,
                                                                   int version) {
        ReviewerDbContract contract = new FactoryReviewerDbContract().newContract();
        return new FactoryDbSpecification().newSpecification(contract, name + "." + ext, version);
    }
}
