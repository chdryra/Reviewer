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
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation.RepositoryError;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .RepositoryMutableCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryMutable;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
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
    public void addReview(final Review review, RepositoryMutableCallback callback) {
        mDb.addReview(mReviewsFactory.newFbReview(review, mTagsManager), addCallback(callback));
    }

    @Override
    public void removeReview(ReviewId reviewId, RepositoryMutableCallback callback) {
        mDb.deleteReview(reviewId.toString(), deleteCallback(callback));
    }

    @Override
    public void getReview(ReviewId id, final RepositoryCallback callback) {
        mDb.getReview(id.toString(), reviewCallback(callback));
    }

    @Override
    public void getReviews(final RepositoryCallback callback) {
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
    private FirebaseDbImpl.GetCallback reviewCallback(final RepositoryCallback callback) {
        return new FirebaseDbImpl.GetCallback() {
            @Override
            public void onReview(FbReview fbReview, @Nullable FirebaseError error) {
                RepositoryError repoError = RepositoryError.none();
                if (error != null) repoError = RepositoryError.error(error.getMessage());
                callback.onFetchedFromRepo(recreateReview(fbReview), repoError);
            }
        };
    }

    private Review recreateReview(FbReview fbReview) {
        ReviewDataHolder holder = mReviewsFactory.newReviewDataHolder(fbReview, mTagsManager);
        return mRecreater.recreateReview(holder);
    }

    @NonNull
    private FirebaseDbImpl.GetCollectionCallback reviewCollectionCallback(final RepositoryCallback
                                                                                  callback) {
        final ArrayList<Review> reviews = new ArrayList<>();
        return new FirebaseDbImpl.GetCollectionCallback() {
            @Override
            public void onReviewCollection(Collection<FbReview> fetched, @Nullable
            FirebaseError error) {
                for (FbReview review : fetched) {
                    reviews.add(recreateReview(review));
                }

                RepositoryError repoError = RepositoryError.none();
                if (error != null) repoError = RepositoryError.error(error.getMessage());

                callback.onCollectionFetchedFromRepo(reviews, repoError);
            }
        };
    }

    @NonNull
    private FirebaseDbImpl.AddCallback addCallback(final RepositoryMutableCallback callback) {
        return new FirebaseDbImpl.AddCallback() {
            @Override
            public void onReviewAdded(FbReview fbReview, @Nullable FirebaseError error) {
                Review review = recreateReview(fbReview);
                if (error != null) {
                    callback.onAdded(review, RepositoryError.error(error.getMessage()));
                } else {
                    callback.onAdded(review, RepositoryError.none());
                    notifyOnAddReview(review);
                }
            }
        };
    }

    @NonNull
    private FirebaseDbImpl.DeleteCallback deleteCallback(final RepositoryMutableCallback callback) {
        return new FirebaseDbImpl.DeleteCallback() {
            @Override
            public void onReviewDeleted(String reviewId, @Nullable FirebaseError error) {
                DatumReviewId id = new DatumReviewId(reviewId);
                if (error != null) {
                    callback.onRemoved(id, RepositoryError.error(error.getMessage()));
                } else {
                    callback.onRemoved(id, RepositoryError.none());
                    notifyOnDeleteReview(id);
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
