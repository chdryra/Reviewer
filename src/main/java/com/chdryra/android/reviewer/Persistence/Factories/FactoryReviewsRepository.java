/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbAuthored;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbAuthoredLatest;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase.Implementation.LocalReviewerDb.Implementation.ReviewerDbRepository;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Persistence.Implementation.FeedRepository;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsRepositoryCached;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
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
    private final FactoryReviewsCache mCacheFactory;

    public FactoryReviewsRepository(FactoryReviewsCache cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    public ReviewsSource newReviewsSource(ReviewsRepository reviewsRepo,
                                          AuthorsRepository authorsRepo,
                                          FactoryReviews reviewsFactory) {
        return new ReviewsSourceImpl(reviewsRepo, authorsRepo, reviewsFactory);
    }

    public ReviewsRepository newCachedRepo(ReviewsRepository archive,
                                              ReviewsCache cache,
                                              FactoryReviews reviewsFactory) {
        return new ReviewsRepositoryCached<>(cache, archive, reviewsFactory);
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }

    public ReferencesRepository newAuthorsLatestRepo(AuthorId authorId, ReviewerDbRepository repo) {
        return new ReviewerDbAuthoredLatest(new ReviewerDbAuthored(authorId, repo));
    }

    public ReferencesRepository newAuthorsRepo(AuthorId authorId, ReviewerDbRepository repo) {
        return new ReviewerDbAuthored(authorId, repo);
    }

    public ReferencesRepository newFeedLatest(AuthorId usersId, RefAuthorList following, ReviewsRepository masterRepo) {
        return new FeedRepository(usersId, following, masterRepo, masterRepo.getLatestForAuthor(usersId));
    }
}
