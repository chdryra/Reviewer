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
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.FirebaseDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 23/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseDbImpl implements FirebaseDb {
    private static final String REVIEWS_ROOT = "Reviews";
    private Firebase mDataRoot;
    private FactoryFbReview mReviewsFactory;
    private FirebaseValidator mValidator;

    public FirebaseDbImpl(Firebase dataRoot, FactoryFbReview reviewsFactory, FirebaseValidator validator) {
        mDataRoot = dataRoot;
        mReviewsFactory = reviewsFactory;
        mValidator = validator;
    }

    @Override
    public void addReview(final Review review, TagsManager tagsManager, AddCallback callback) {
        FbReview fbReview = mReviewsFactory.newFirebaseReview(review);
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(review.getReviewId().toString());
        root.setValue(fbReview, newAddListener(review, callback));
    }

    @Override
    public void deleteReview(ReviewId reviewId, DeleteCallback callback) {
        Firebase ref = mDataRoot.child(REVIEWS_ROOT).child(reviewId.toString());
        ref.removeValue(newDeleteListener(reviewId, callback));
    }

    @Override
    public void getReview(ReviewId id, TagsManager tagsManager, GetCallback callback) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT).child(id.toString());
        root.addValueEventListener(newGetListener(tagsManager, callback));
    }

    @Override
    public void getReviews(TagsManager tagsManager, GetCollectionCallback callback) {
        Firebase root = mDataRoot.child(REVIEWS_ROOT);
        root.addValueEventListener(newGetCollectionListener(callback));
    }

    @NonNull
    private ValueEventListener newGetListener(final TagsManager tagsManager, final GetCallback listener) {
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FbReview fbReview = dataSnapshot.getValue(FbReview.class);
                ReviewDataHolder review = mReviewsFactory.newReview(fbReview);
                listener.onReview(review, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReview(mReviewsFactory.nullReview(), firebaseError);
            }
        };
    }

    @NonNull
    private ValueEventListener newGetCollectionListener(final GetCollectionCallback listener) {
        final ArrayList<ReviewDataHolder> reviews = new ArrayList<>();
        return new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Author author = postSnapshot.child("author").getValue(Author.class);
                    FbReview review = postSnapshot.getValue(FbReview.class);
                    if (mValidator.isValid(review)) reviews.add(mReviewsFactory.newReview(review));
                }

                listener.onReviewCollection(reviews, null);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                listener.onReviewCollection(reviews, firebaseError);
            }
        };
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
