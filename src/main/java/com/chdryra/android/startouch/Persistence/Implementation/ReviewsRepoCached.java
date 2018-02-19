/*
 * Copyright (c) Rizwan Choudrey 2018 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

import java.util.List;

/**
 * Created by: Rizwan Choudrey
 * On: 31/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsRepoCached<T extends ReviewsRepo> extends RepoReadableBasic implements
        ReviewsRepo {
    private final ReviewsCache mCache;
    private final T mArchive;
    private final FactoryReviews mReviewsFactory;

    public ReviewsRepoCached(ReviewsCache cache,
                             T archive,
                             FactoryReviews reviewsFactory,
                             ReviewDereferencer dereferencer,
                             SizeReferencer sizeReferencer) {
        super(dereferencer, sizeReferencer);
        mCache = cache;
        mArchive = archive;
        mReviewsFactory = reviewsFactory;
    }

    @Override
    public ReviewsRepoReadable getRepoForAuthor(AuthorId authorId) {
        return mArchive.getRepoForAuthor(authorId);
    }

    @Override
    public ReviewsRepoWriteable getRepoForUser(UserSession session) {
        return mArchive.getRepoForUser(session);
    }

    @Override
    public ReviewCollection getCollectionForAuthor(AuthorId authorId, String name) {
        return mArchive.getCollectionForAuthor(authorId, name);
    }

    @Override
    public void subscribe(ItemSubscriber<ReviewReference> binder) {
        mArchive.subscribe(binder);
    }

    @Override
    public void unsubscribe(ItemSubscriber<ReviewReference> binder) {
        mArchive.unsubscribe(binder);
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        if (mCache.contains(reviewId)) {
            ReviewReference reference = mReviewsFactory.asReference(mCache.get(reviewId));
            callback.onRepoCallback(new RepoResult(reference));
        } else {
            mArchive.getReference(reviewId, callback);
        }
    }

    @Override
    protected void doDereferencing(DereferenceCallback<List<ReviewReference>> callback) {
        mArchive.dereference(callback);
    }
}
