/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepo implements ReviewsRepositoryMutable {
    private FirebaseDb mDb;
    private TagsManager mTagsManager;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public FirebaseReviewsRepo(FirebaseDb db,
                               TagsManager tagsManager) {
        mDb = db;
        mTagsManager = tagsManager;
        mObservers = new ArrayList<>();
    }

    @Override
    public void addReview(final Review review, RepositoryMutableCallback callback) {
        mDb.addReview(review, mTagsManager, addListener(callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        mDb.deleteReview(reviewId, deleteListener(callback));
    }

    @Override
    public void getReview(ReviewId id, RepositoryCallback callback) {

    }

    @Override
    public void getReviews(RepositoryCallback callback) {

    }

    @Override
    public TagsManager getTagsManager() {
        return mTagsManager;
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        if (!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
    }

    @NonNull
    private FirebaseDb.AddListener addListener(final RepositoryMutableCallback callback) {
        return new FirebaseDb.AddListener() {
            @Override
            public void onReviewAdded(Review review, @Nullable FirebaseError error) {
                if (error != null) {
                    callback.onAdded(review, RepositoryError.error(error.getMessage()));
                } else {
                    callback.onAdded(review, RepositoryError.none());
                    notifyOnAddReview(review);
                }
            }
        };
    }

    @NonNull
    private FirebaseDb.DeleteListener deleteListener(final RepositoryMutableCallback callback) {
        return new FirebaseDb.DeleteListener() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, @Nullable FirebaseError error) {
                if (error != null) {
                    callback.onRemoved(reviewId, RepositoryError.error(error.getMessage()));
                } else {
                    callback.onRemoved(reviewId, RepositoryError.none());
                    notifyOnDeleteReview(reviewId);
                }
            }
        };
    }

    private void notifyOnAddReview(Review review) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewAdded(review);
        }
    }

    private void notifyOnDeleteReview(ReviewId reviewId) {
        for (ReviewsRepositoryObserver observer : mObservers) {
            observer.onReviewRemoved(reviewId);
        }
    }
}
