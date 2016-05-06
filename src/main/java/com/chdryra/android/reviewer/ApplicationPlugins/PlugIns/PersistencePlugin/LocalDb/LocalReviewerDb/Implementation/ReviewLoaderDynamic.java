/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Implementation;


import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewLoader;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.ReviewerDbReadable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.LocalReviewerDb.Interfaces.RowReview;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.LocalDb.RelationalDb.RelationalDbPlugin.Api.TableTransactor;

/**
 * Created by: Rizwan Choudrey
 * On: 07/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLoaderDynamic implements ReviewLoader {
    private FactoryReviewNode mNodeFactory;

    public ReviewLoaderDynamic(FactoryReviewNode nodeFactory) {
        mNodeFactory = nodeFactory;
    }

    @Override
    public Review loadReview(RowReview reviewRow, ReviewerDbReadable database, TableTransactor db) {
        return new ReviewDb(reviewRow, database, mNodeFactory);
    }
}
