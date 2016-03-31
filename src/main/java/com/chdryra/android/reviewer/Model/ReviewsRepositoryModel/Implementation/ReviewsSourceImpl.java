/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces
        .ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsSourceCallback;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewsSourceImpl implements ReviewsSource {
    private ReviewsRepository mRepository;
    private FactoryReviews mReviewFactory;

    public ReviewsSourceImpl(ReviewsRepository repository,
                             FactoryReviews reviewFactory) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
    }

    @Override
    public void asMetaReview(ReviewId id, final ReviewsSourceCallback callback) {
        asMetaReviewNullable(id, new ReviewsSourceCallback() {
            @Override
            public void onMetaReview(@Nullable ReviewNode review, RepositoryMessage message) {
                ReviewNode node = review != null ? review : mReviewFactory.getNullNode();
                callback.onMetaReview(node, message);
            }
        });
    }

    @Override
    public void asMetaReview(final VerboseDataReview datum, final String subjectIfMetaOfItems,
                             final ReviewsSourceCallback callback) {
        ReviewsSourceCallback sourceCallback = getReviewSourceCallback(callback);
        ReviewId id = getSingleSourceId(datum);
        if (id != null) {
            asMetaReviewNullable(id, sourceCallback);
        } else {
            getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subjectIfMetaOfItems, sourceCallback);
        }
    }

    @Override
    public void getMetaReview(final VerboseIdableCollection data, final String subject,
                              final ReviewsSourceCallback callback) {
        getUniqueReviews(data, new RepositoryCallback() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage result) {

            }

            @Override
            public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage
                    result) {
                ReviewNode meta = reviews.size() > 0 ?
                        mReviewFactory.createMetaReview(reviews, subject) : mReviewFactory
                        .getNullNode();
                callback.onMetaReview(meta, result);
            }
        });
    }

    @Override
    public void getReview(final ReviewId reviewId, final RepositoryCallback callback) {
        getReviewNullable(reviewId, new RepositoryCallback() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage result) {
                if (review == null) review = mReviewFactory.getNullReview();
                callback.onFetchedFromRepo(review, result);
            }

            @Override
            public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage
                    result) {

            }
        });

    }

    @Override
    public void getReviews(RepositoryCallback callback) {
        mRepository.getReviews(callback);
    }

    @Override
    public TagsManager getTagsManager() {
        return mRepository.getTagsManager();
    }

    @Override
    public void registerObserver(ReviewsRepositoryObserver observer) {
        mRepository.registerObserver(observer);
    }

    @Override
    public void unregisterObserver(ReviewsRepositoryObserver observer) {
        mRepository.unregisterObserver(observer);
    }

    @Nullable
    private ReviewId getSingleSourceId(VerboseDataReview datum) {
        ReviewId id = null;
        if (datum.isVerboseCollection() && datum.hasElements()) {
            VerboseIdableCollection<? extends VerboseDataReview> data =
                    (VerboseIdableCollection<? extends VerboseDataReview>) datum;
            id = data.getItem(0).getReviewId();
            for (VerboseDataReview element : data) {
                if (!element.getReviewId().equals(id)) {
                    id = null;
                    break;
                }
            }
        }

        return id;
    }

    @NonNull
    private ReviewsSourceCallback getReviewSourceCallback(final ReviewsSourceCallback callback) {
        return new ReviewsSourceCallback() {
            @Override
            public void onMetaReview(@Nullable ReviewNode review, RepositoryMessage message) {
                callback.onMetaReview(review, message);
            }
        };
    }

    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
        mRepository.getReview(id, new RepositoryCallback() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage result) {
                ReviewNode ret = review != null ? mReviewFactory.createMetaReview(review) : null;
                callback.onMetaReview(ret, result);
            }

            @Override
            public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage
                    result) {

            }
        });
    }

    private void getReviewNullable(ReviewId reviewId, RepositoryCallback callback) {
        mRepository.getReview(reviewId, callback);
    }

    private void getUniqueReviews(final VerboseIdableCollection data,
                                  final RepositoryCallback callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReviewNullable(data.getItem(i).getReviewId(), uniqueCallback);
        }
    }

    private class UniqueCallback implements RepositoryCallback {
        private final ArrayList<RepositoryMessage> mErrors;
        private final RepositoryCallback mFinalCallback;
        private final Set<Review> mFetched;
        private final int mMaxReviews;
        private int mCurrentIndex;

        public UniqueCallback(int maxReviews, RepositoryCallback finalCallback) {
            mMaxReviews = maxReviews;
            mFinalCallback = finalCallback;
            mCurrentIndex = 0;
            mErrors = new ArrayList<>();
            mFetched = new HashSet<>();
        }

        @Override
        public void onFetchedFromRepo(@Nullable Review review, RepositoryMessage error) {
            mCurrentIndex++;

            if (review != null && !error.isError()) {
                mFetched.add(review);
            } else {
                mErrors.add(error);
            }

            if (mCurrentIndex == mMaxReviews) {
                RepositoryMessage result = mErrors.size() > 0 ?
                        RepositoryMessage.error("Errors fetching some reviews")
                        : RepositoryMessage.ok(mMaxReviews + " reviews fetched");
                mFinalCallback.onCollectionFetchedFromRepo(mFetched, result);
            }
        }

        @Override
        public void onCollectionFetchedFromRepo(Collection<Review> reviews, RepositoryMessage
                result) {

        }
    }
}
