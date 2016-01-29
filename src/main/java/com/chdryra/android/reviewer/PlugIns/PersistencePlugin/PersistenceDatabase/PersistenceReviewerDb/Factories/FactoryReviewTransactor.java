/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.PersistenceReviewerDb.Factories;


import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.ReviewDeleterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.ReviewInserterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.ReviewLoaderDynamic;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase
        .PersistenceReviewerDb.Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.PersistenceDatabase.RelationalDb
        .Interfaces.FactoryDbTableRow;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewTransactor {
    private FactoryDbTableRow mRowFactory;

    public FactoryReviewTransactor(FactoryDbTableRow rowFactory) {
        mRowFactory = rowFactory;
    }

    public ReviewTransactor newStaticLoader(ReviewRecreater reviewBuilder,
                                            TagsManager tagsManager,
                                            DataValidator validator) {
        return newTransactor(new ReviewLoaderStatic(reviewBuilder, validator), tagsManager);
    }

    public ReviewTransactor newDynamicLoader(FactoryReviewNode nodeFactory, TagsManager tagsManager) {
        return newTransactor(new ReviewLoaderDynamic(nodeFactory), tagsManager);
    }

    private ReviewTransactor newTransactor(ReviewLoader loader, TagsManager tagsManager) {
        return new ReviewTransactor( loader,
                new ReviewInserterImpl(mRowFactory),
                new ReviewDeleterImpl(mRowFactory), tagsManager);
    }
}
