/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend
        .Implementation.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend
        .Implementation.ReviewDb;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface BackendReviewsDb {
    interface AddReviewCallback {
        void onReviewAdded(ReviewDb review, @Nullable BackendError error);
    }

    interface DeleteReviewCallback {
        void onReviewDeleted(String reviewId, @Nullable BackendError error);
    }

    interface GetReviewCallback {
        void onReview(ReviewDb review, @Nullable BackendError error);
    }

    interface GetCollectionCallback {
        void onReviewCollection(Collection<ReviewDb> reviews, @Nullable BackendError error);
    }

    void addReview(ReviewDb review, AddReviewCallback callback);

    void deleteReview(String reviewId, DeleteReviewCallback callback);

    void getReview(String id, GetReviewCallback callback);

    void getReviews(GetCollectionCallback callback);

    void getReviewsList(GetCollectionCallback callback);

    void registerObserver(DbObserver<ReviewDb> observer);

    void unregisterObserver(DbObserver<ReviewDb> observer);
}
