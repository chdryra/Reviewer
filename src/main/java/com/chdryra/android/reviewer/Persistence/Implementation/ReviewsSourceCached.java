/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import com.chdryra.android.reviewer.Authentication.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepoMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceCached<T extends ReviewsSource> implements ReviewsSource {
    private final ReviewsCache mCache;
    private final T mArchive;
    private final FactoryReviews mReviewsFactory;
    private final ReviewDereferencer mDereferencer;

    public ReviewsSourceCached(ReviewsCache cache,
                               T archive,
                               FactoryReviews reviewsFactory, ReviewDereferencer dereferencer) {
        mCache = cache;
        mArchive = archive;
        mReviewsFactory = reviewsFactory;
        mDereferencer = dereferencer;
    }

    @Override
    public ReviewsRepo getReviewsByAuthor(AuthorId authorId) {
        return mArchive.getReviewsByAuthor(authorId);
    }

    @Override
    public ReviewsRepoMutable getMutableRepoForUser(UserSession session) {
        return mArchive.getMutableRepoForUser(session);
    }

    @Override
    public ReviewCollection getCollectionForAuthor(AuthorId authorId, String name) {
        return mArchive.getCollectionForAuthor(authorId, name);
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
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        if(mCache.contains(reviewId)) {
            ReviewReference reference = mReviewsFactory.asReference(mCache.get(reviewId));
            callback.onRepoCallback(new RepoResult(reference));
        } else {
            mArchive.getReference(reviewId, callback);
        }
    }

    @Override
    public void getReview(ReviewId reviewId, RepoCallback callback) {
        mDereferencer.getReview(reviewId, this, callback);
    }
}
