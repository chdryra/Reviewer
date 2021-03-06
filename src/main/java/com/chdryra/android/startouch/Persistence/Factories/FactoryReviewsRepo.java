/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Factories;

import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Authentication.Interfaces.SocialProfileRef;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Persistence.Implementation.FeedRepo;
import com.chdryra.android.startouch.Persistence.Implementation.RepoCollection;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewDereferencer;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewNodeRepoImpl;
import com.chdryra.android.startouch.Persistence.Implementation.ReviewsRepoCached;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
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

    public ReviewNodeRepo newReviewsNodeRepo(ReviewsRepo reviewsRepo,
                                             AuthorsRepo authorsRepo,
                                             FactoryReviews reviewsFactory) {
        return new ReviewNodeRepoImpl(reviewsRepo, authorsRepo, reviewsFactory, newDereferencer()
                , newSizeReferencer());
    }

    public ReviewsRepo newCachedRepo(ReviewsRepo archive,
                                     ReviewsCache cache,
                                     FactoryReviews reviewsFactory) {
        return new ReviewsRepoCached<>(cache, archive, reviewsFactory, newDereferencer(),
                newSizeReferencer());
    }

    public ReviewsCache newCache() {
        return mCacheFactory.newCache();
    }


    public ReviewsRepoReadable newFeed(SocialProfileRef profile, ReviewsRepo masterRepo) {
        return new FeedRepo(profile.getAuthorId(), profile.getFollowing(),
                masterRepo, newDereferencer(), newSizeReferencer());
    }

    public ReviewDereferencer newDereferencer() {
        return new ReviewDereferencer();
    }

    public SizeReferencer newSizeReferencer() {
        return new SizeReferencer();
    }

    public RepoCollection<AuthorId> newRepoCollection() {
        return new RepoCollection<>(newDereferencer(), newSizeReferencer());
    }
}
