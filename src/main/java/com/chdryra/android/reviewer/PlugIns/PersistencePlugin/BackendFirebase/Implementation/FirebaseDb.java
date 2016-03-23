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
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseDb {
    private static final String REVIEWS_ROOT = "Reviews";
    private Firebase mDataRoot;
    private FactoryUserReview mReviewFactory;

    public interface AddListener {
        void onReviewAdded(Review review, @Nullable FirebaseError error);
    }

    public interface DeleteListener {
        void onReviewDeleted(ReviewId reviewId, @Nullable FirebaseError error);
    }

    public interface GetListener {
        void onReview(Review review, @Nullable FirebaseError error);
    }

    public interface GetCollectionListener {
        void onReviewCollection(Collection<Review> review, @Nullable FirebaseError error);
    }

    public FirebaseDb(Firebase dataRoot,
                      FactoryUserReview reviewFactory) {
        mDataRoot = dataRoot;
        mReviewFactory = reviewFactory;
    }

    public void addReview(final Review review, TagsManager tagsManager, AddListener listener) {
        UserReview fbReview = mReviewFactory.newReview(review);
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(review.getReviewId().toString());
        ref.setValue(fbReview, newAddListener(review, listener));
    }

    public void deleteReview(ReviewId reviewId, DeleteListener listener) {
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(reviewId.toString());
        ref.removeValue(newDeleteListener(reviewId, listener));
    }

    public void getReview(ReviewId id, GetListener listener) {

    }

    public void getReviews() {

    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final Review review,
                                                       final AddListener listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewAdded(review, firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final ReviewId reviewId,
                                                          final DeleteListener listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewDeleted(reviewId, firebaseError);
            }
        };
    }
}
