/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsModel.Review;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.Interfaces.ReviewsRepositoryModel
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.ItemTagCollection;
import com.chdryra.android.reviewer.Model.Interfaces.TagsModel.TagsManager;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepo implements ReviewsRepositoryMutable{
    private static final String REVIEWS_ROOT = "Reviews";
    private Firebase mDataRoot;
    private FactoryUserReview mReviewFactory;
    private TagsManager mTagsManager;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public FirebaseReviewsRepo(Firebase dataRoot,
                               FactoryUserReview reviewFactory,
                               TagsManager tagsManager) {
        mDataRoot = dataRoot;
        mReviewFactory = reviewFactory;
        mTagsManager = tagsManager;
        mObservers = new ArrayList<>();
    }

    @Override
    public void addReview(final Review review) {
        UserReview fbReview = mReviewFactory.newReview(review);
        ItemTagCollection tags = mTagsManager.getTags(review.getReviewId().toString());
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(review.getReviewId().toString());
        ref.setValue(fbReview, newAddListener(review));
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final Review review) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    notifyOnAddReview(review);
                }
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final ReviewId reviewId) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be deleted. " + firebaseError.getMessage());
                } else {
                    notifyOnDeleteReview(reviewId);
                }
            }
        };
    }

    @Override
    public void removeReview(ReviewId reviewId) {
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(reviewId.toString());
        ref.removeValue(newDeleteListener(reviewId));
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
        if(!mObservers.contains(observer)) mObservers.add(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mObservers.remove(observer);
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
