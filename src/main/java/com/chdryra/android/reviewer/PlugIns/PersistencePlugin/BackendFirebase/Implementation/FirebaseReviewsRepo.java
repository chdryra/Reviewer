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
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;

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
    public void addReview(final Review review) {
        mDb.addReview(review, mTagsManager, addListener());
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        mDb.deleteReview(reviewId, deleteListener());
    }

    @Override
    public Review getReview(ReviewId id) {
        return null;
    }

    @Override
    public Collection<Review> getReviews() {
        return null;
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
    private FirebaseDb.AddListener addListener() {
        return new FirebaseDb.AddListener() {
            @Override
            public void onReviewAdded(Review review, @Nullable FirebaseError error) {
                if (error != null) {
                    handleFirebaseError(error);
                } else {
                    notifyOnAddReview(review);
                }
            }
        };
    }

    @NonNull
    private FirebaseDb.DeleteListener deleteListener() {
        return new FirebaseDb.DeleteListener() {
            @Override
            public void onReviewDeleted(ReviewId reviewId, @Nullable FirebaseError error) {
                if (error != null) {
                    handleFirebaseError(error);
                } else {
                    notifyOnDeleteReview(reviewId);
                }
            }
        };
    }

    private void handleFirebaseError(FirebaseError error) {
        //TODO handle firebase error
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
