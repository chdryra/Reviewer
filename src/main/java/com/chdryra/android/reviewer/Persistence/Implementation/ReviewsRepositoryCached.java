/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepositoryCached<T extends ReviewsRepository>
        implements ReviewsRepository {
    private final ReviewsCache mCache;
    private final T mArchive;
    private final FactoryReviews mReviewsFactory;

    public ReviewsRepositoryCached(ReviewsCache cache,
                                   T archive,
                                   FactoryReviews reviewsFactory) {
        mCache = cache;
        mArchive = archive;
        mReviewsFactory = reviewsFactory;
    }

    @Override
    public ReferencesRepository getLatestForAuthor(AuthorId authorId) {
        return mArchive.getLatestForAuthor(authorId);
    }

    @Override
    public ReferencesRepository getRepositoryForAuthor(AuthorId authorId) {
        return mArchive.getRepositoryForAuthor(authorId);
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        return mArchive.getMutableRepository(session);
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        mArchive.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        mArchive.unsubscribe(subscriber);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        if(mCache.contains(reviewId)) {
            ReviewReference reference = mReviewsFactory.asReference(mCache.get(reviewId));
            callback.onRepositoryCallback(new RepositoryResult(reference));
        } else {
            mArchive.getReference(reviewId, callback);
        }
    }
}
