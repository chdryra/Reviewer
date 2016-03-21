/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationContexts.Implementation;

import android.content.Context;

import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.ModelContext;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsFeed;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviewsRepository;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsFeedMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Api.PersistencePlugin;

/**
 * Created by: Rizwan Choudrey
 * On: 04/12/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReleasePersistenceContext extends PersistenceContextBasic {

    public ReleasePersistenceContext(Context context,
                                     ModelContext model,
                                     PersistencePlugin persistencePlugin) {
        ReviewsRepositoryMutable localPersistence
                = persistencePlugin.newLocalPersistence(context, model);

        setAuthorsFeed(localPersistence, model.getReviewsFactory());

        setBackendRepository(persistencePlugin.newBackendPersistence(context, model));

        FactoryReviewsRepository repoFactory = new FactoryReviewsRepository();
        setReviewsSource(repoFactory.newReviewsSource(localPersistence, model.getReviewsFactory()));
    }

    private void setAuthorsFeed(ReviewsRepositoryMutable sourceAndDestination,
                                FactoryReviews reviewsFactory) {
        FactoryReviewsFeed feedFactory = new FactoryReviewsFeed(reviewsFactory);
        ReviewsFeedMutable reviewsFeed = feedFactory.newMutableFeed(sourceAndDestination);

        setAuthorsFeed(reviewsFeed);
    }
}