/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Factories;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefAuthorList;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Persistence.Implementation.FeedRepo;
import com.chdryra.android.reviewer.Persistence.Implementation.RepoCollection;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.reviewer.Persistence.Implementation.ReviewsSourceCached;
import com.chdryra.android.reviewer.Persistence.Implementation.NodeRepoImpl;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.NodeRepo;

/**
 * Created by: Rizwan Choudrey
 * On: 16/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryReviewsRepo {
    private final FactoryReviewsCache mCacheFactory;

    public FactoryReviewsRepo(FactoryReviewsCache cacheFactory) {
        mCacheFactory = cacheFactory;
    }

    public NodeRepo newReviewsSource(ReviewsSource reviewsRepo,
                                     AuthorsRepo authorsRepo,
                                     FactoryReviews reviewsFactory) {
        return new NodeRepoImpl(reviewsRepo, authorsRepo, reviewsFactory, newDereferencer());
    }

    public ReviewsSource newCachedRepo(ReviewsSource archive,
                                       ReviewsCache cache,
                                       FactoryReviews reviewsFactory) {
        return new ReviewsSourceCached<>(cache, archive, reviewsFactory, newDereferencer());
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }


    public ReviewsRepo newFeed(AuthorId usersId, RefAuthorList following, ReviewsSource masterRepo) {
        return new FeedRepo(usersId, following, masterRepo, masterRepo.getReviewsByAuthor(usersId), newRepoCollection());
    }

    @NonNull
    public ReviewDereferencer newDereferencer() {
        return new ReviewDereferencer();
    }

    public RepoCollection<AuthorId> newRepoCollection() {
        return new RepoCollection<>(newDereferencer());
    }
}
