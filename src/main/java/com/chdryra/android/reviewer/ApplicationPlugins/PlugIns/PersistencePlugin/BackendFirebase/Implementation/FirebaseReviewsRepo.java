/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.FirebaseDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.BackendFirebase.Interfaces.FirebaseDbObserver;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepo implements ReviewsRepositoryMutable, FirebaseDbObserver {
    private static final String REVIEWS_FOUND = "Reviews found";
    private static final String ERROR_FETCHING_REVIEWS = "Error fetching reviews: ";
    private static final String ERROR_FETCHING_REVIEW = "Error fetching review :";
    private static final String REVIEW_FOUND = "Review found";
    public static final String REMOVED = "removed";

    private FirebaseDb mDb;
    private FactoryFbReview mReviewsFactory;
    private TagsManager mTagsManager;
    private FirebaseReviewMaker mRecreater;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public FirebaseReviewsRepo(FirebaseDb db,
                               FactoryFbReview reviewsFactory,
                               TagsManager tagsManager,
                               FirebaseReviewMaker recreater) {
        mDb = db;
        mReviewsFactory = reviewsFactory;
        mTagsManager = tagsManager;
        mRecreater = recreater;
        mObservers = new ArrayList<>();
        mDb.registerObserver(this);
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
        mDb.getReviewsList(reviewListCallback(callback));
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

    @Override
    public void onReviewAdded(FbReview review) {
        notifyOnAddReview(recreateReview(review));
    }

    @Override
    public void onReviewChanged(FbReview review) {

    }

    @Override
    public void onReviewRemoved(FbReview review) {
        notifyOnDeleteReview(new DatumReviewId(review.getReviewId()));
    }

    @NonNull
    private FirebaseReviewsDb.GetCallback reviewCallback(final CallbackRepository callback) {
        return new FirebaseReviewsDb.GetCallback() {
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
        mTagsManager.tagItem(fbReview.getReviewId(), new ArrayList<>(fbReview.getTags()));
        return mRecreater.makeReview(holder);
    }

    private Review recreatePartialReview(FbReview fbReview) {
        return mRecreater.makePartialReview(fbReview.getReviewId(),fbReview.getRating());
    }

    @NonNull
    private FirebaseReviewsDb.GetCollectionCallback reviewListCallback(final CallbackRepository
                                                                                     callback) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new FirebaseReviewsDb.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Collection<FbReview> fetched, @Nullable
            FirebaseError error) {
                for (FbReview review : fetched) {
                    reviews.add(recreatePartialReview(review));
                }

                CallbackMessage result = getCollectionCallbackMessage(error);

                callback.onFetchedFromRepo(reviews, result);
            }
        };
    }

    @NonNull
    private CallbackMessage getCollectionCallbackMessage(@Nullable FirebaseError error) {
        return error != null ?
                            CallbackMessage.error(ERROR_FETCHING_REVIEWS + error.getMessage())
                            : CallbackMessage.ok(REVIEWS_FOUND);
    }

    @NonNull
    private FirebaseReviewsDb.AddCallback addCallback(final CallbackRepositoryMutable callback) {
        return new FirebaseReviewsDb.AddCallback() {
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
    private FirebaseReviewsDb.DeleteCallback deleteCallback(final CallbackRepositoryMutable callback) {
        return new FirebaseReviewsDb.DeleteCallback() {
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
