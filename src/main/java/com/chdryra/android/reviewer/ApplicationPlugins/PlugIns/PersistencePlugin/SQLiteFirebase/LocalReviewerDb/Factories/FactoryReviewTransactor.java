/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Factories;



import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.RelationalDb.Interfaces.FactoryDbTableRow;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewDeleterImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewInserterImpl;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewLoaderDynamic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.LocalReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewMaker;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTransactor {
    private final FactoryDbTableRow mRowFactory;

    public FactoryReviewTransactor(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    public ReviewTransactor newStaticLoader(ReviewMaker reviewBuilder,
                                            DataValidator validator) {
        return newTransactor(new ReviewLoaderStatic(reviewBuilder, validator));
    }

    public ReviewTransactor newDynamicLoader() {
        return newTransactor(new ReviewLoaderDynamic());
    }

    private ReviewTransactor newTransactor(ReviewLoader loader) {
        return new ReviewTransactor( loader,
                new ReviewInserterImpl(mRowFactory),
                new ReviewDeleterImpl(mRowFactory));
    }
}
