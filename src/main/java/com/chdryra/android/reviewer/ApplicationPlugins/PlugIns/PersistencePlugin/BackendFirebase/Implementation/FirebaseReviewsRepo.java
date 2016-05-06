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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.BackendError;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.DbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.ApiClasses.ReviewDb;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseReviewsRepo implements ReviewsRepositoryMutable, DbObserver<ReviewDb> {
    private static final String REVIEWS_FOUND = "Reviews found";
    private static final String ERROR_FETCHING_REVIEWS = "Error fetching reviews: ";
    private static final String ERROR_FETCHING_REVIEW = "Error fetching review :";
    private static final String REVIEW_FOUND = "Review found";
    public static final String REMOVED = "removed";

    private BackendReviewsDb mDb;
    private FactoryReviewDb mReviewsFactory;
    private TagsManager mTagsManager;
    private FirebaseReviewMaker mRecreater;
    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public FirebaseReviewsRepo(BackendReviewsDb db,
                               FactoryReviewDb reviewsFactory,
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
        mDb.getReviews(reviewCollectionCallback(callback));
        //mDb.getReviewsList(reviewListCallback(callback));
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
    public void onAdded(ReviewDb review) {
        notifyOnAddReview(recreateReview(review));
    }

    @Override
    public void onChanged(ReviewDb review) {

    }

    @Override
    public void onRemoved(ReviewDb review) {
        notifyOnDeleteReview(new DatumReviewId(review.getReviewId()));
    }

    @NonNull
    private BackendReviewsDb.GetReviewCallback reviewCallback(final CallbackRepository callback) {
        return new BackendReviewsDb.GetReviewCallback() {
            @Override
            public void onReview(ReviewDb reviewDb, @Nullable BackendError error) {
                CallbackMessage result = error != null ?
                        CallbackMessage.error(ERROR_FETCHING_REVIEW + error.getMessage())
                        : CallbackMessage.ok(REVIEW_FOUND);
                callback.onFetchedFromRepo(recreateReview(reviewDb), result);
            }
        };
    }

    private Review recreateReview(ReviewDb reviewDb) {
        ReviewDataHolder holder = mReviewsFactory.newReviewDataHolder(reviewDb, mTagsManager);
        mTagsManager.tagItem(reviewDb.getReviewId(), new ArrayList<>(reviewDb.getTags()));
        return mRecreater.makeReview(holder);
    }

    private Review recreateLazyReview(ReviewDb reviewDb) {
        return mRecreater.makeLazyReview(reviewDb.getReviewId(), reviewDb.getSubject(),
                reviewDb.getRating(), reviewDb.getPublishDate(), this);
    }

    @NonNull
    private FirebaseReviewsDbImpl.GetCollectionCallback reviewListCallback(final CallbackRepository
                                                                                     callback) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new FirebaseReviewsDbImpl.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Collection<ReviewDb> fetched, @Nullable
            BackendError error) {
                for (ReviewDb review : fetched) {
                    reviews.add(recreateLazyReview(review));
                }

                CallbackMessage result = getCollectionCallbackMessage(error);

                callback.onFetchedFromRepo(reviews, result);
            }
        };
    }

    @NonNull
    private FirebaseReviewsDbImpl.GetCollectionCallback reviewCollectionCallback(final CallbackRepository
                                                                               callback) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new FirebaseReviewsDbImpl.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Collection<ReviewDb> fetched, @Nullable
            BackendError error) {
                for (ReviewDb review : fetched) {
                    reviews.add(recreateReview(review));
                }

                CallbackMessage result = getCollectionCallbackMessage(error);

                callback.onFetchedFromRepo(reviews, result);
            }
        };
    }

    @NonNull
    private CallbackMessage getCollectionCallbackMessage(@Nullable BackendError error) {
        return error != null ?
                            CallbackMessage.error(ERROR_FETCHING_REVIEWS + error.getMessage())
                            : CallbackMessage.ok(REVIEWS_FOUND);
    }

    @NonNull
    private BackendReviewsDb.AddReviewCallback addCallback(final CallbackRepositoryMutable callback) {
        return new BackendReviewsDb.AddReviewCallback() {
            @Override
            public void onReviewAdded(ReviewDb reviewDb, @Nullable BackendError error) {
                Review review = recreateReview(reviewDb);
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
    private BackendReviewsDb.DeleteReviewCallback deleteCallback(final CallbackRepositoryMutable callback) {
        return new BackendReviewsDb.DeleteReviewCallback() {
            @Override
            public void onReviewDeleted(String reviewId, @Nullable BackendError error) {
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
