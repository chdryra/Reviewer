/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Model.ReviewsRepositoryModel.Interfaces.ReviewsRepositoryObserver;
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
            public void onMetaReview(@Nullable ReviewNode review, RepositoryError error) {
                ReviewNode node = review != null ? review : mReviewFactory.getNullNode();
                callback.onMetaReview(node, error);
            }
        });
    }

    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
        mRepository.getReview(id, new RepositoryCallback() {
            @Override
            public void onFetched(@Nullable Review review, RepositoryError error) {
                ReviewNode ret = review != null ? mReviewFactory.createMetaReview(review) : null;
                callback.onMetaReview(ret, error);
            }

            @Override
            public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {

            }
        });
    }

    @Override
    public void asMetaReview(final VerboseDataReview datum, final String subjectIfMetaOfItems,
                             final ReviewsSourceCallback callback) {
        asMetaReviewNullable(datum.getReviewId(), new ReviewsSourceCallback() {
            @Override
            public void onMetaReview(@Nullable ReviewNode review, RepositoryError error) {
                if (review == null && datum.isVerboseCollection()) {
                    getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                            subjectIfMetaOfItems, new ReviewsSourceCallback() {
                                @Override
                                public void onMetaReview(@Nullable ReviewNode review, RepositoryError error) {
                                    callback.onMetaReview(review, error);
                                }
                            });
                }
            }
        });


    }

    @Override
    public void getMetaReview(final VerboseIdableCollection data, final String subject,
                              final ReviewsSourceCallback callback) {
        getUniqueReviews(data, new RepositoryCallback() {
            @Override
            public void onFetched(@Nullable Review review, RepositoryError error) {

            }

            @Override
            public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {
                ReviewNode meta = reviews.size() > 0 ?
                        mReviewFactory.createMetaReview(reviews, subject) : mReviewFactory.getNullNode();
                callback.onMetaReview(meta, error);
            }
        });
    }

    @Override
    public void getReview(final ReviewId reviewId, final RepositoryCallback callback) {
        getReviewNullable(reviewId, new RepositoryCallback() {
            @Override
            public void onFetched(@Nullable Review review, RepositoryError error) {
                if (review == null) review = mReviewFactory.getNullReview();
                callback.onFetched(review, error);
            }

            @Override
            public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {

            }
        });

    }

    private void getReviewNullable(ReviewId reviewId, RepositoryCallback callback) {
        mRepository.getReview(reviewId, callback);
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

    private void getUniqueReviews(final VerboseIdableCollection data,
                                                      final RepositoryCallback callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReviewNullable(data.getItem(i).getReviewId(), uniqueCallback);
        }
    }

    private class UniqueCallback implements  RepositoryCallback {
        private final ArrayList<RepositoryError> mErrors;
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
        public void onFetched(@Nullable Review review, RepositoryError error) {
            mCurrentIndex++;

            if (review != null && !error.isError()) {
                mFetched.add(review);
            } else{
                mErrors.add(error);
            }

            if(mCurrentIndex == mMaxReviews) {
                RepositoryError finalError = mErrors.size() > 0 ?
                        RepositoryError.error("Errors fetching some reviews")
                        : RepositoryError.none();
                mFinalCallback.onCollectionFetched(mFetched, finalError);
            }
        }

        @Override
        public void onCollectionFetched(Collection<Review> reviews, RepositoryError error) {

        }
    }
}
