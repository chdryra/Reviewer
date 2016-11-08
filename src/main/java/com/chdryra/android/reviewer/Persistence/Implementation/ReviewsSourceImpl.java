/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.View
        .ReviewTreeSourceCallback;

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
    private final ReviewsRepository mReviewsRepo;
    private final AuthorsRepository mAuthorsRepo;
    private final FactoryReviews mReviewsFactory;

    public ReviewsSourceImpl(ReviewsRepository reviewsRepo, AuthorsRepository authorsRepo,
                             FactoryReviews reviewsFactory) {
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mReviewsFactory = reviewsFactory;
    }

    @Override
    public TagsManager getTagsManager() {
        return mReviewsRepo.getTagsManager();
    }

    @Override
    public void subscribe(ReviewsSubscriber subscriber) {
        mReviewsRepo.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        mReviewsRepo.unsubscribe(subscriber);
    }

    @Override
    public void asMetaReview(ReviewId id, final ReviewsSourceCallback callback) {
        asMetaReviewNullable(id, callback);
    }

    @Override
    public ReviewNode asMetaReview(ReviewId id) {
        ReviewTreeSourceCallback fetchingNode = newFetchingNode();
        asMetaReview(id, fetchingNode);
        return fetchingNode;
    }

    @Override
    public ReviewNode asMetaReview(AuthorId id) {
        ReferencesRepository repo = getRepositoryForAuthor(id);
        return mReviewsFactory.createAuthorsTree(id, repo, mAuthorsRepo);
    }

    @Override
    public ReviewNode asMetaReview(final VerboseDataReview datum, final String
            subjectIfMetaOfItems) {
        if (!datum.isCollection()) {
            IdableCollection<VerboseDataReview> data = new IdableDataCollection<>();
            data.add(datum);
            return getMetaReview(data, subjectIfMetaOfItems);
        } else {
            return getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subjectIfMetaOfItems);
        }
//        ReviewId id = getSingleSourceId(datum);
//        if (id != null) {
//            asMetaReviewNullable(id, callback);
//        } else {
//            getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
//                    subjectIfMetaOfItems, callback);
//        }
    }

    @Override
    public MutableRepository getMutableRepository(UserSession session) {
        return mReviewsRepo.getMutableRepository(session);
    }

    @Override
    public void getReference(ReviewId reviewId, RepositoryCallback callback) {
        mReviewsRepo.getReference(reviewId, callback);
    }

    @Override
    public ReferencesRepository getLatestForAuthor(AuthorId authorId) {
        return mReviewsRepo.getLatestForAuthor(authorId);
    }

    @Override
    public ReferencesRepository getRepositoryForAuthor(AuthorId authorId) {
        return mReviewsRepo.getRepositoryForAuthor(authorId);
    }

    @Override
    public ReviewNode getMetaReview(IdableCollection<?> data, String subject) {
        ReviewTreeSourceCallback fetchingNode = newFetchingNode();
        getMetaReview(data, subject, newFetchingNode());
        return fetchingNode;
    }

    @NonNull
    private ReviewTreeSourceCallback newFetchingNode() {
        Review fetching = mReviewsFactory.createUserReview(Strings.FETCHING, 0f,
                new ArrayList<DataCriterion>(), new ArrayList<DataComment>(),
                new ArrayList<DataImage>(), new ArrayList<DataFact>(),
                new ArrayList<DataLocation>(), true);

        ReviewReference reference = mReviewsFactory.asReference(fetching, getTagsManager());
        ReviewNodeComponent node = mReviewsFactory.createLeafNode(reference);

        return new ReviewTreeSourceCallback(node);
    }
//
//    @Nullable
//    private ReviewId getSingleSourceId(VerboseDataReview datum) {
//        ReviewId id = datum.getReviewId();
//        if (datum.isCollection() && datum.hasElements()) {
//            VerboseIdableCollection<? extends VerboseDataReview> data =
//                    (VerboseIdableCollection<? extends VerboseDataReview>) datum;
//            id = data.getItem(0).getReviewId();
//            for (VerboseDataReview element : data) {
//                if (!element.getReviewId().equals(id)) {
//                    id = null;
//                    break;
//                }
//            }
//        }
//
//        return id;
//    }

    private void getMetaReview(final IdableCollection<?> data, final String subject,
                               final ReviewsSourceCallback callback) {
        getUniqueReviews(data, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                if (!result.isError()) {
                    Collection<ReviewReference> reviews = result.getReferences();
                    ReviewNode meta = mReviewsFactory.createTree(reviews, subject);
                    result = new RepositoryResult(meta, result.getMessage());
                }

                callback.onMetaReviewCallback(result);
            }
        });
    }

    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
        mReviewsRepo.getReference(id, new RepositoryCallback() {

            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference review = result.getReference();
                RepositoryResult repoResult;
                if (result.isError() || review == null) {
                    repoResult = result;
                } else {
                    ReviewNode node = mReviewsFactory.createTree(review);
                    repoResult = new RepositoryResult(node, result.getMessage());
                }

                callback.onMetaReviewCallback(repoResult);
            }
        });
    }

    private void getUniqueReviews(final IdableCollection<?> data,
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

        private UniqueCallback(int maxReviews, RepositoryCallback finalCallback) {
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
                mFinalCallback.onRepositoryCallback(new RepositoryResult(mFetched, message));
            }
        }
    }
}
