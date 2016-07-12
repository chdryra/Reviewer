/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.ApplicationContexts.Interfaces.UserSession;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Interfaces.ReviewSubscriber;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;

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

    public ReviewsSourceImpl(ReviewsRepository repository, FactoryReviews reviewFactory) {
        mRepository = repository;
        mReviewFactory = reviewFactory;
    }

    @Override
    public TagsManager getTagsManager() {
        return mRepository.getTagsManager();
    }

    @Override
    public void bind(ReviewSubscriber subscriber) {
        mRepository.bind(subscriber);
    }

    @Override
    public void unbind(ReviewSubscriber subscriber) {
        mRepository.unbind(subscriber);
    }

    @Override
    public void asMetaReview(ReviewId id, final ReviewsSourceCallback callback) {
        asMetaReviewNullable(id, callback);
    }

    @Override
    public void asMetaReview(final VerboseDataReview datum, final String subjectIfMetaOfItems,
                             final ReviewsSourceCallback callback) {
        ReviewId id = getSingleSourceId(datum);
        if (id != null) {
            asMetaReviewNullable(id, callback);
        } else {
            getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subjectIfMetaOfItems, callback);
        }
    }

    @Override
    public void getMetaReview(final VerboseIdableCollection data, final String subject,
                              final ReviewsSourceCallback callback) {
        getUniqueReviews(data, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                if (!result.isError()) {
                    Collection<ReviewReference> reviews = result.getReferences();
                    ReviewNode meta = mReviewFactory.createMetaTree(reviews, subject);
                    result = new RepositoryResult(meta, result.getMessage());
                }

                callback.onMetaReviewCallback(result);
            }
        });
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        return mRepository.getMutableRepository(session);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        mRepository.getReference(reviewId, callback);
    }

    @Override
    public AuthorsRepository getRepository(DataAuthor author) {
        return mRepository.getRepository(author);
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

    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
        mRepository.getReference(id, new RepositoryCallback() {

            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference review = result.getReference();
                RepositoryResult repoResult;
                if(result.isError() || review == null) {
                    repoResult = result;
                } else {
                    ReviewNodeComponent node = mReviewFactory.createMetaTree(review);
                    repoResult = new RepositoryResult(node, result.getMessage());
                }

                callback.onMetaReviewCallback(repoResult);
            }
        });
    }

    private void getUniqueReviews(final VerboseIdableCollection data,
                                  final RepositoryCallback callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReference(data.getItem(i).getReviewId(), uniqueCallback);
        }
    }

    private class UniqueCallback implements RepositoryCallback {
        private final ArrayList<CallbackMessage> mErrors;
        private final RepositoryCallback mFinalCallback;
        private final Set<ReviewReference> mFetched;
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

            ReviewReference review = result.getReference();
            if (review != null && !result.isError()) {
                mFetched.add(review);
            } else {
                mErrors.add(result.getMessage());
            }

            if (mCurrentIndex == mMaxReviews) {
                CallbackMessage message = mErrors.size() > 0 ?
                        CallbackMessage.error("Errors fetching some reviews")
                        : CallbackMessage.ok(mMaxReviews + " reviews fetched");
                mFinalCallback.onRepositoryCallback(new RepositoryResult(mFetched, null, message));
            }
        }
    }
}
