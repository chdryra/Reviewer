/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbAuthored;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbMutable;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsRepositoryCached;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepository {
    private FactoryReviewsCache mCacheFactory;

    public FactoryReviewsRepository(FactoryReviewsCache cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    public ReviewsSource newReviewsSource(ReviewsRepository repository,
                                          FactoryReviews reviewsFactory) {
        return new ReviewsSourceImpl(repository, reviewsFactory);
    }

    public ReviewsRepository newCachedRepo(ReviewsRepository archive,
                                              ReviewsCache cache,
                                              FactoryReviews reviewsFactory,
                                              TagsManager tagsManager) {
        return new ReviewsRepositoryCached<>(cache, archive, reviewsFactory, tagsManager);
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }

    public ReferencesRepository newAuthorsRepo(AuthorId authorId, ReviewerDbRepository repo) {
        return new ReviewerDbAuthored(authorId, repo);
    }

    public MutableRepository newMutableRepo(AuthorId authorId, ReviewerDbRepository repo) {
        return new ReviewerDbMutable(authorId, repo);
    }
}
