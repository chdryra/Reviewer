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

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepositoryObserver;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackReviewsSource;
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
    public void asMetaReview(ReviewId id, final CallbackReviewsSource callback) {
        asMetaReviewNullable(id, getCallbackWrapper(callback));
    }

    @Override
    public void asMetaReview(final VerboseDataReview datum, final String subjectIfMetaOfItems,
                             final CallbackReviewsSource callback) {
        CallbackReviewsSource sourceCallback = getCallbackWrapper(callback);
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
                              final CallbackReviewsSource callback) {
        getUniqueReviews(data, new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {

            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage
                    result) {
                ReviewNode meta = reviews.size() > 0 ?
                        mReviewFactory.createMetaReview(reviews, subject)
                        : mReviewFactory.getNullNode();
                callback.onMetaReviewCallback(meta, result);
            }
        });
    }

    @Override
    public void getReview(final ReviewId reviewId, final CallbackRepository callback) {
        getReviewNullable(reviewId, new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
                if (review == null) review = mReviewFactory.getNullReview();
                callback.onFetchedFromRepo(review, result);
            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage
                    result) {

            }
        });

    }

    @Override
    public void getReviews(CallbackRepository callback) {
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
    private CallbackReviewsSource getCallbackWrapper(final CallbackReviewsSource callback) {
        return new CallbackReviewsSource() {
            @Override
            public void onMetaReviewCallback(@Nullable ReviewNode review, CallbackMessage message) {
                ReviewNode node = review != null ? review : mReviewFactory.getNullNode();
                callback.onMetaReviewCallback(node, message);
            }
        };
    }

    private void asMetaReviewNullable(ReviewId id, final CallbackReviewsSource callback) {
        mRepository.getReview(id, new CallbackRepository() {
            @Override
            public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
                ReviewNode ret = review != null ? mReviewFactory.createMetaReview(review)
                        : mReviewFactory.getNullNode();
                callback.onMetaReviewCallback(ret, result);
            }

            @Override
            public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage
                    result) {

            }
        });
    }

    private void getReviewNullable(ReviewId reviewId, CallbackRepository callback) {
        mRepository.getReview(reviewId, callback);
    }

    private void getUniqueReviews(final VerboseIdableCollection data,
                                  final CallbackRepository callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReviewNullable(data.getItem(i).getReviewId(), uniqueCallback);
        }
    }

    private class UniqueCallback implements CallbackRepository {
        private final ArrayList<CallbackMessage> mErrors;
        private final CallbackRepository mFinalCallback;
        private final Set<Review> mFetched;
        private final int mMaxReviews;
        private int mCurrentIndex;

        public UniqueCallback(int maxReviews, CallbackRepository finalCallback) {
            mMaxReviews = maxReviews;
            mFinalCallback = finalCallback;
            mCurrentIndex = 0;
            mErrors = new ArrayList<>();
            mFetched = new HashSet<>();
        }

        @Override
        public void onFetchedFromRepo(@Nullable Review review, CallbackMessage error) {
            mCurrentIndex++;

            if (review != null && !error.isError()) {
                mFetched.add(review);
            } else {
                mErrors.add(error);
            }

            if (mCurrentIndex == mMaxReviews) {
                CallbackMessage result = mErrors.size() > 0 ?
                        CallbackMessage.error("Errors fetching some reviews")
                        : CallbackMessage.ok(mMaxReviews + " reviews fetched");
                mFinalCallback.onFetchedFromRepo(mFetched, result);
            }
        }

        @Override
        public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage
                result) {

        }
    }
}
