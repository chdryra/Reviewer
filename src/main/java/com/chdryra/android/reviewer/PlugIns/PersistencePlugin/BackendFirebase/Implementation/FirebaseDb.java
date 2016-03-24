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
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseDb {
    private static final String REVIEWS_ROOT = "Reviews";
    private Firebase mDataRoot;
    private FactoryFbReview mReviewFactory;

    public interface AddCallback {
        void onReviewAdded(Review review, @Nullable FirebaseError error);
    }

    public interface DeleteCallback {
        void onReviewDeleted(ReviewId reviewId, @Nullable FirebaseError error);
    }

    public interface GetCallback {
        void onReview(ReviewDataHolder review, @Nullable FirebaseError error);
    }

    public interface GetCollectionCallback {
        void onReviewCollection(Collection<ReviewDataHolder> reviews, @Nullable FirebaseError error);
    }

    public FirebaseDb(Firebase dataRoot,
                      FactoryFbReview reviewFactory) {
        mDataRoot = dataRoot;
        mReviewFactory = reviewFactory;
    }

    public void addReview(final Review review, TagsManager tagsManager, AddCallback listener) {
        FbReview fbReview = mReviewFactory.newFirebaseReview(review);
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(review.getReviewId().toString());
        root.setValue(fbReview, newAddListener(review, listener));
    }

    public void deleteReview(ReviewId reviewId, DeleteCallback listener) {
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(reviewId.toString());
        ref.removeValue(newDeleteListener(reviewId, listener));
    }

    public void getReview(ReviewId id, final GetCallback listener) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(id.toString());
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FbReview post = dataSnapshot.getValue(FbReview.class);
                listener.onReview(mReviewFactory.newReview(post), null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getReviews(final GetCollectionCallback listener) {
        final ArrayList<ReviewDataHolder> reviews = new ArrayList<>();
        Firebase root = mDataRoot.child(REVIEWS_ROOT);
        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    FbReview post = postSnapshot.getValue(FbReview.class);
                    if(post.isValid()) reviews.add(mReviewFactory.newReview(post));
                }

                listener.onReviewCollection(reviews, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReviewCollection(reviews, firebaseError);
            }
        });
    }

    @NonNull
    private Firebase.CompletionListener newAddListener(final Review review,
                                                       final AddCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewAdded(review, firebaseError);
            }
        };
    }

    @NonNull
    private Firebase.CompletionListener newDeleteListener(final ReviewId reviewId,
                                                          final DeleteCallback listener) {
        return new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                listener.onReviewDeleted(reviewId, firebaseError);
            }
        };
    }
}
