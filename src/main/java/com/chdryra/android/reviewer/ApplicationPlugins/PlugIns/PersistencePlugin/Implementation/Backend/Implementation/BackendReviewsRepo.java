/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.FunctionPointer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Factories.FactoryReviewDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Interfaces.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Interfaces.DbObserver;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.SQLiteFirebase
        .Implementation.BackendFirebase.Implementation.ReviewReference;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewDataHolder;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.LazyReviewMaker;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 16/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendReviewsRepo implements ReviewsRepositoryMutable, DbObserver<ReviewDb> {
    public static final String REMOVED = "removed";
    private static final String REVIEWS_FOUND = "Reviews found";
    private static final String ERROR_FETCHING_REVIEWS = "Error fetching reviews: ";
    private static final String ERROR_FETCHING_REVIEW = "Error fetching review :";
    private static final String REVIEW_FOUND = "Review found";
    private BackendReviewsDb mDb;
    private FactoryReviewDb mReviewsFactory;
    private TagsManager mTagsManager;
    private LazyReviewMaker mRecreater;

    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public BackendReviewsRepo(BackendReviewsDb db,
                              FactoryReviewDb reviewsFactory,
                              TagsManager tagsManager,
                              LazyReviewMaker recreater) {
        mDb = db;
        mReviewsFactory = reviewsFactory;
        mTagsManager = tagsManager;
        mRecreater = recreater;
        mObservers = new ArrayList<>();
        mDb.registerObserver(this);
    }

    public void downloadReview(ReviewId id, final RepositoryCallback callback) {
        mDb.getReview(id.toString(), reviewCallback(callback));
    }

    @Override
    public void addReview(final Review review, RepositoryMutableCallback callback) {
        mDb.addReview(mReviewsFactory.newReviewDb(review, mTagsManager), addCallback(callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        mDb.deleteReview(reviewId.toString(), deleteCallback(callback));
    }

    @Override
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        mDb.getReview(id.toString(), reviewCallbackLazy(callback));
    }

    @Override
    public void getReviews(final RepositoryCallback callback) {
        getReviewsGreedy(null, callback);
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        getReviewsGreedy(author, callback);
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
        if (mObservers.contains(observer)) mObservers.remove(observer);
    }

    @Override
    public void onAdded(ReviewDb review) {
        notifyOnAddReview(recreateLazyReview(review));
    }

    @Override
    public void onChanged(ReviewDb review) {

    }

    @Override
    public void onRemoved(ReviewDb review) {
        notifyOnDeleteReview(new DatumReviewId(review.getReviewId()));
    }

    private void getReviewsLazy(@Nullable DataAuthor author, RepositoryCallback callback) {
        mDb.getReviewsList(author == null ? null : new Author(author),
                reviewCollectionCallback(callback, new FunctionPointer<ReviewDb, Review>() {
                    @Override
                    public Review execute(ReviewDb data) {
                        return recreateLazyReview(data);
                    }
                }));
    }

    private void getReviewsGreedy(@Nullable DataAuthor author, RepositoryCallback callback) {
        mDb.getReviews(author == null ? null : new Author(author),
                reviewCollectionCallback(callback, new FunctionPointer<ReviewDb, Review>() {
                    @Override
                    public Review execute(ReviewDb data) {
                        return recreateReview(data);
                    }
                }));
    }

    @NonNull
    private BackendReviewsDb.GetReviewCallback reviewCallbackLazy(final RepositoryCallback
                                                                              callback) {
        return new BackendReviewsDb.GetReviewCallback() {
            @Override
            public void onReview(ReviewReference reference, @Nullable BackendError error) {
                CallbackMessage result = error != null ?
                        CallbackMessage.error(ERROR_FETCHING_REVIEW + error.getMessage())
                        : CallbackMessage.ok(REVIEW_FOUND);
                callback.onRepositoryCallback(new RepositoryResult(recreateLazyReview(reference),
                        result));
            }
        };
    }

    @NonNull
    private BackendReviewsDb.GetReviewCallback reviewCallback(final RepositoryCallback callback) {
        return new BackendReviewsDb.GetReviewCallback() {
            @Override
            public void onReview(ReviewReference reference, @Nullable BackendError error) {
                CallbackMessage result = error != null ?
                        CallbackMessage.error(ERROR_FETCHING_REVIEW + error.getMessage())
                        : CallbackMessage.ok(REVIEW_FOUND);
                callback.onRepositoryCallback(new RepositoryResult(recreateReview(reference),
                        result));
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
                reviewDb.getRating(), reviewDb.getAuthor(), reviewDb.getPublishDate(), this);
    }

    @NonNull
    private BackendReviewsDb.GetCollectionCallback
    reviewCollectionCallback(final RepositoryCallback callback,
                             final FunctionPointer<ReviewDb, Review> recreateFp) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new BackendReviewsDb.GetCollectionCallback() {
            @Override
            public void onReviewCollection(@Nullable Author author,
                                           Collection<ReviewReference> fetched,
                                           @Nullable BackendError error) {
                for (ReviewReference review : fetched) {
                    reviews.add(recreateFp.execute(review));
                }

                CallbackMessage result = getCollectionCallbackMessage(error);

                DataAuthor da = author == null ? null : author.toDataAuthor();
                callback.onRepositoryCallback(new RepositoryResult(da, reviews, result));
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
    private BackendReviewsDb.AddReviewCallback addCallback(final RepositoryMutableCallback
                                                                   callback) {
        return new BackendReviewsDb.AddReviewCallback() {
            @Override
            public void onReviewAdded(ReviewDb reviewDb, @Nullable BackendError error) {
                Review review = recreateReview(reviewDb);
                CallbackMessage result;
                if (error != null) {
                    result = CallbackMessage.error(error.getMessage());
                } else {
                    result = CallbackMessage.ok();
                    notifyOnAddReview(review);
                }
                callback.onAddedToRepoCallback(new RepositoryResult(review, result));
            }
        };
    }

    @NonNull
    private BackendReviewsDb.DeleteReviewCallback deleteCallback(final RepositoryMutableCallback
                                                                         callback) {
        return new BackendReviewsDb.DeleteReviewCallback() {
            @Override
            public void onReviewDeleted(String reviewId, @Nullable BackendError error) {
                DatumReviewId id = new DatumReviewId(reviewId);
                if (error != null) {
                    callback.onRemovedFromRepoCallback(new RepositoryResult(id, CallbackMessage
                            .error(error.getMessage())));
                } else {
                    notifyOnDeleteReview(id);
                    callback.onRemovedFromRepoCallback(new RepositoryResult(id, CallbackMessage
                            .ok(reviewId + ": " + REMOVED)));
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
