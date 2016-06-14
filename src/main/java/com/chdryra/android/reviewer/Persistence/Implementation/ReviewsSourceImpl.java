/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;

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
        asMetaReviewNullable(id, getCallbackWrapper(callback));
    }

    @Override
    public void asMetaReview(final VerboseDataReview datum, final String subjectIfMetaOfItems,
                             final ReviewsSourceCallback callback) {
        ReviewsSourceCallback sourceCallback = getCallbackWrapper(callback);
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
            public void onRepositoryCallback(RepositoryResult result) {
                if (!result.isError()) {
                    Collection<Review> reviews = result.getReviews();
                    ReviewNode meta = reviews.size() > 0 ?
                            mReviewFactory.createMetaReview(reviews, subject)
                            : mReviewFactory.getNullNode();
                    result = new RepositoryResult(meta, result.getMessage());
                }

                callback.onMetaReviewCallback(result);
            }
        });
    }

    @Override
    public void getReview(final ReviewId reviewId, final RepositoryCallback callback) {
        getReviewNullable(reviewId, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                if(result.isError()) {
                    Review review = mReviewFactory.getNullReview();
                    result = new RepositoryResult(review, result.getMessage());
                }

                callback.onRepositoryCallback(result);
            }
        });

    }

    @Override
    public void getReviews(DataAuthor author, RepositoryCallback callback) {
        mRepository.getReviews(author, callback);
    }

    @Override
    public void getReference(ReviewId id, RepositoryCallback callback) {
        mRepository.getReference(id, callback);
    }

    @Override
    public void getReferences(DataAuthor author, RepositoryCallback callback) {
        mRepository.getReferences(author, callback);
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
        ReviewId id = datum.getReviewId();
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
    private ReviewsSourceCallback getCallbackWrapper(final ReviewsSourceCallback callback) {
        return new ReviewsSourceCallback() {
            @Override
            public void onMetaReviewCallback(RepositoryResult result) {
                if(result.isError()) {
                    ReviewNode node = mReviewFactory.getNullNode();
                    result = new RepositoryResult(node, result.getMessage());
                }

                callback.onMetaReviewCallback(result);
            }
        };
    }

    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
        mRepository.getReview(id, new RepositoryCallback() {

            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                Review review = result.getReview();
                boolean isError = result.isError();
                ReviewNode node = isError || review == null ? mReviewFactory.getNullNode()
                        : mReviewFactory.createMetaReview(review);

                callback.onMetaReviewCallback(new RepositoryResult(node, result.getMessage()));
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
        private final ArrayList<CallbackMessage> mErrors;
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
        public void onRepositoryCallback(RepositoryResult result) {
            mCurrentIndex++;

            Review review = result.getReview();
            if (review != null && !result.isError()) {
                mFetched.add(review);
            } else {
                mErrors.add(result.getMessage());
            }

            if (mCurrentIndex == mMaxReviews) {
                CallbackMessage message = mErrors.size() > 0 ?
                        CallbackMessage.error("Errors fetching some reviews")
                        : CallbackMessage.ok(mMaxReviews + " reviews fetched");
                mFinalCallback.onRepositoryCallback(new RepositoryResult(mFetched, message));
            }
        }
    }
}
