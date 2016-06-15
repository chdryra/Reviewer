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
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Factories.BackendReviewConverter;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.BackendReviewsDb;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.DbObserver;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Implementation.RepositoryResult;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewReference;
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
    private BackendReviewConverter mConverter;

    private ArrayList<ReviewsRepositoryObserver> mObservers;

    public BackendReviewsRepo(BackendReviewsDb db,
                              BackendReviewConverter converter) {
        mDb = db;
        mConverter = converter;
        mObservers = new ArrayList<>();
        mDb.registerObserver(this);
    }

    public void downloadReview(ReviewId id, final RepositoryCallback callback) {
        mDb.getReview(id.toString(), reviewCallback(id, callback));
    }

    @Override
    public void addReview(final Review review, RepositoryMutableCallback callback) {
        mDb.addReview(mConverter.convert(review), addCallback(callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        mDb.deleteReview(reviewId.toString(), deleteCallback(callback));
    }

    @Override
    public void getReview(ReviewId reviewId, final RepositoryCallback callback) {
        mDb.getReview(reviewId.toString(), reviewCallback(reviewId, callback));
    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        mDb.getReviews(new Author(author), reviewCollectionCallback(callback));
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        mDb.getReview(reviewId.toString(), referenceCallback(reviewId, callback));
    }

    @Override
    public void getReferences(DataAuthor author, RepositoryCallback callback) {
        mDb.getReviews(new Author(author), referenceCollectionCallback(callback));
    }

    @Override
    public TagsManager getTagsManager() {
        return mConverter.getTagsManager();
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
        notifyOnAddReview(mConverter.convert(review));
    }

    @Override
    public void onChanged(ReviewDb review) {

    }

    @Override
    public void onRemoved(ReviewDb review) {
        notifyOnDeleteReview(new DatumReviewId(review.getReviewId()));
    }

    @NonNull
    private BackendReviewsDb.GetReviewCallback referenceCallback(final ReviewId id,
                                                                 final RepositoryCallback
                                                                         callback) {
        return new BackendReviewsDb.GetReviewCallback() {
            @Override
            public void onReview(ReviewReference reference, @Nullable BackendError error) {
                CallbackMessage result = getFetchedMessage(error);
                if (result.isError()) {
                    callback.onRepositoryCallback(new RepositoryResult(id, result));
                } else {
                    callback.onRepositoryCallback(new RepositoryResult(reference, result));
                }
            }
        };
    }

    @NonNull
    private BackendReviewsDb.GetReviewCallback reviewCallback(final ReviewId id,
                                                              final RepositoryCallback callback) {
        return new BackendReviewsDb.GetReviewCallback() {
            @Override
            public void onReview(ReviewReference reference, @Nullable BackendError error) {
                CallbackMessage result = getFetchedMessage(error);
                if (result.isError()) {
                    callback.onRepositoryCallback(new RepositoryResult(id, result));
                } else {
                    reference.dereference(dereferenceCallback(callback, id));
                }
            }
        };
    }

    @NonNull
    private ReviewReference.DereferenceCallback dereferenceCallback(final RepositoryCallback
                                                                            callback, final
                                                                    ReviewId id) {
        return new ReviewReference.DereferenceCallback() {
            @Override
            public void onDereferenced(Review review, CallbackMessage message) {
                if (message.isError()) {
                    callback.onRepositoryCallback(new RepositoryResult(id, message));
                } else {
                    callback.onRepositoryCallback(new RepositoryResult(review, message));
                }
            }
        };
    }

    @NonNull
    private CallbackMessage getFetchedMessage(@Nullable BackendError error) {
        return error != null ?
                CallbackMessage.error(ERROR_FETCHING_REVIEW + error.getMessage())
                : CallbackMessage.ok(REVIEW_FOUND);
    }

    @NonNull
    private BackendReviewsDb.GetCollectionCallback
    referenceCollectionCallback(final RepositoryCallback callback) {
        return new BackendReviewsDb.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Author author,
                                           Collection<ReviewReference> fetched,
                                           @Nullable BackendError error) {
                CallbackMessage result = getCollectionCallbackMessage(error);
                DataAuthor da = author == null ? null : author.toDataAuthor();
                callback.onRepositoryCallback(new RepositoryResult(fetched, da, result));
            }
        };
    }

    @NonNull
    private BackendReviewsDb.GetCollectionCallback
    reviewCollectionCallback(final RepositoryCallback callback) {
        return new BackendReviewsDb.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Author author,
                                           Collection<ReviewReference> fetched,
                                           @Nullable BackendError error) {
                CallbackMessage result = getCollectionCallbackMessage(error);
                DataAuthor da = author.toDataAuthor();
                final ArrayList<Review> reviews = new ArrayList<>();
                if (result.isError()) {
                    callback.onRepositoryCallback(new RepositoryResult(reviews, result));
                } else {
                    dereferenceCollectionCallback(fetched, reviews);
                    callback.onRepositoryCallback(new RepositoryResult(da, reviews, result));
                }
            }
        };
    }

    private void dereferenceCollectionCallback(Collection<ReviewReference> fetched, final
    ArrayList<Review> reviews) {
        for (ReviewReference reference : fetched) {
            reference.dereference(new ReviewReference.DereferenceCallback() {
                @Override
                public void onDereferenced(Review review, CallbackMessage message) {
                    if (!message.isError()) reviews.add(review);
                }
            });
        }
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
                Review review = mConverter.convert(reviewDb);
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
