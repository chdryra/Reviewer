/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Factories;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.AuthorListRef;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Persistence.Implementation.FeedRepo;
import com.chdryra.android.startouch.Persistence.Implementation.RepoCollection;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewsNodeRepoImpl;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewsRepoCached;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;

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

    public ReviewsNodeRepo newReviewsNodeRepo(ReviewsRepo reviewsRepo,
                                              AuthorsRepo authorsRepo,
                                              FactoryReviews reviewsFactory) {
        return new ReviewsNodeRepoImpl(reviewsRepo, authorsRepo, reviewsFactory, newDereferencer());
    }

    public ReviewsRepo newCachedRepo(ReviewsRepo archive,
                                     ReviewsCache cache,
                                     FactoryReviews reviewsFactory) {
        return new ReviewsRepoCached<>(cache, archive, reviewsFactory, newDereferencer());
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }


    public ReviewsRepoReadable newFeed(AuthorId usersId, AuthorListRef following, ReviewsRepo masterRepo) {
        return new FeedRepo(usersId, following, masterRepo, masterRepo.getReviewsByAuthor(usersId), newRepoCollection());
    }

    public ReviewDereferencer newDereferencer() {
        return new ReviewDereferencer();
    }

    public RepoCollection<AuthorId> newRepoCollection() {
        return new RepoCollection<>(newDereferencer());
    }
}
