/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories;



import android.content.Context;

import com.chdryra.android.startouch.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.ContractorDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.FactoryContractor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Api.RelationalDbPlugin;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Factories.FactoryDbSpecification;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.DbSpecification;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewerDbRepo;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDb;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewerDbContract;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewMaker;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

/**
 * Created by: Rizwan Choudrey
 * On: 21/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryLocalReviewerDb {
    private final FactoryContractor mContractorFactory;
    private final String mExt;
    private final FactoryReviewerDb mDbFactory;
    private final FactoryReviewTransactor mReviewTransactor;
    private final String mPersistenceName;
    private final int mPersistenceVer;
    private final Context mContext;

    public FactoryLocalReviewerDb(Context context, String name, int version, RelationalDbPlugin dbPlugin) {
        FactoryReviewerDbTableRow rowFactory = new FactoryReviewerDbTableRow();
        mDbFactory = new FactoryReviewerDb(rowFactory);
        mReviewTransactor = new FactoryReviewTransactor(rowFactory);
        mContractorFactory = dbPlugin.getContractorFactory();
        mPersistenceName = name;
        mPersistenceVer = version;
        mExt = dbPlugin.getDbNameExtension();
        mContext = context;
    }

    public ReviewsRepoWriteable newPersistence(ModelContext model, DataValidator validator) {
        FactoryReviews reviewsFactory = model.getReviewsFactory();
        ReviewerDb db = newReviewerDb(mPersistenceName, mPersistenceVer, reviewsFactory, validator);
        FactoryDbReference referenceFactory = new FactoryDbReference(model.getReferencesFactory());
        return new ReviewerDbRepo(new ReviewDereferencer(), new SizeReferencer(), db, referenceFactory);
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
