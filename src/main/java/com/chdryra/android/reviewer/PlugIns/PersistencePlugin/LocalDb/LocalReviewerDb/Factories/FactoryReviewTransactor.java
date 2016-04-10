/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Factories;



import com.chdryra.android.reviewer.DataDefinitions.Implementation.DataValidator;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewDeleterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewInserterImpl;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewLoaderDynamic;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewLoaderStatic;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation.ReviewTransactor;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewRecreater;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalDb.RelationalDb.Interfaces.FactoryDbTableRow;

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
                                            DataValidator validator) {
        return newTransactor(new ReviewLoaderStatic(reviewBuilder, validator));
    }

    public ReviewTransactor newDynamicLoader(FactoryReviewNode nodeFactory, TagsManager tagsManager) {
        return newTransactor(new ReviewLoaderDynamic(nodeFactory));
    }

    private ReviewTransactor newTransactor(ReviewLoader loader) {
        return new ReviewTransactor( loader,
                new ReviewInserterImpl(mRowFactory),
                new ReviewDeleterImpl(mRowFactory));
    }
}
