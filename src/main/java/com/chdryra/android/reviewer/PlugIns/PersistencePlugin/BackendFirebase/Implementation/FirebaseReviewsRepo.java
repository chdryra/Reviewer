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

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.FirebaseDb;
import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.LocalRelationalDb.ReviewerDb
        .Interfaces.ReviewRecreater;

import com.chdryra.android.reviewer.PlugIns.PersistencePlugin.Utils.ReviewDataHolder;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepo implements ReviewsRepositoryMutable {
    private static final String REVIEWS_FOUND = "Reviews found";
    private static final String ERROR_FETCHING_REVIEWS = "Error fetching reviews: ";
    private static final String ERROR_FETCHING_REVIEW = "Error fetching review :";
    private static final String REVIEW_FOUND = "Review found";
    public static final String REMOVED = "removed";

    private FirebaseDb mDb;
    private FactoryFbReview mReviewsFactory;
    private TagsManager mTagsManager;
    private ReviewRecreater mRecreater;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public FirebaseReviewsRepo(FirebaseDb db,
                               FactoryFbReview reviewsFactory,
                               TagsManager tagsManager,
                               ReviewRecreater recreater) {
        mDb = db;
        mReviewsFactory = reviewsFactory;
        mTagsManager = tagsManager;
        mRecreater = recreater;
        mObservers = new ArrayList<>();
    }

    @Override
    public void addReview(final Review review, CallbackRepositoryMutable callback) {
        mDb.addReview(mReviewsFactory.newFbReview(review, mTagsManager), addCallback(callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, CallbackRepositoryMutable callback) {
        mDb.deleteReview(reviewId.toString(), deleteCallback(callback));
    }

    @Override
    public void getReview(ReviewId id, final CallbackRepository callback) {
        mDb.getReview(id.toString(), reviewCallback(callback));
    }

    @Override
    public void getReviews(final CallbackRepository callback) {
        mDb.getReviews(reviewCollectionCallback(callback));
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
    private FirebaseDbImpl.GetCallback reviewCallback(final CallbackRepository callback) {
        return new FirebaseDbImpl.GetCallback() {
            @Override
            public void onReview(FbReview fbReview, @Nullable FirebaseError error) {
                CallbackMessage result = error != null ?
                        CallbackMessage.error(ERROR_FETCHING_REVIEW + error.getMessage())
                        : CallbackMessage.ok(REVIEW_FOUND);
                callback.onFetchedFromRepo(recreateReview(fbReview), result);
            }
        };
    }

    private Review recreateReview(FbReview fbReview) {
        ReviewDataHolder holder = mReviewsFactory.newReviewDataHolder(fbReview, mTagsManager);
        return mRecreater.recreateReview(holder);
    }

    @NonNull
    private FirebaseDbImpl.GetCollectionCallback reviewCollectionCallback(final CallbackRepository
                                                                                  callback) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new FirebaseDbImpl.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Collection<FbReview> fetched, @Nullable
            FirebaseError error) {
                for (FbReview review : fetched) {
                    reviews.add(recreateReview(review));
                }

                CallbackMessage result = error != null ?
                        CallbackMessage.error(ERROR_FETCHING_REVIEWS + error.getMessage())
                        : CallbackMessage.ok(REVIEWS_FOUND);

                callback.onFetchedFromRepo(reviews, result);
            }
        };
    }

    @NonNull
    private FirebaseDbImpl.AddCallback addCallback(final CallbackRepositoryMutable callback) {
        return new FirebaseDbImpl.AddCallback() {
            @Override
            public void onReviewAdded(FbReview fbReview, @Nullable FirebaseError error) {
                Review review = recreateReview(fbReview);
                CallbackMessage result;
                if(error != null) {
                    result = CallbackMessage.error(error.getMessage());
                } else {
                    result = CallbackMessage.ok(REVIEW_FOUND);
                    notifyOnAddReview(review);
                }
                callback.onAddedCallback(review, result);
            }
        };
    }

    @NonNull
    private FirebaseDbImpl.DeleteCallback deleteCallback(final CallbackRepositoryMutable callback) {
        return new FirebaseDbImpl.DeleteCallback() {
            @Override
            public void onReviewDeleted(String reviewId, @Nullable FirebaseError error) {
                DatumReviewId id = new DatumReviewId(reviewId);
                if (error != null) {
                    callback.onRemovedCallback(id, CallbackMessage.error(error.getMessage()));
                } else {
                    notifyOnDeleteReview(id);
                    callback.onRemovedCallback(id, CallbackMessage.ok(reviewId + ": " + REMOVED));
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
