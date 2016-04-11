/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation.FbReview;
import com.firebase.client.FirebaseError;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 24/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface FirebaseDb {
    interface AddCallback {
        void onReviewAdded(FbReview review, @Nullable FirebaseError error);
    }

    interface DeleteCallback {
        void onReviewDeleted(String reviewId, @Nullable FirebaseError error);
    }

    interface GetCallback {
        void onReview(FbReview review, @Nullable FirebaseError error);
    }

    interface GetCollectionCallback {
        void onReviewCollection(Collection<FbReview> reviews, @Nullable FirebaseError error);
    }

    void addReview(FbReview review, AddCallback callback);

    void deleteReview(String reviewId, DeleteCallback callback);

    void getReview(String id, GetCallback callback);

    void getReviews(GetCollectionCallback callback);

    void registerObserver(FirebaseDbObserver observer);

    void unregisterObserver(FirebaseDbObserver observer);
}
