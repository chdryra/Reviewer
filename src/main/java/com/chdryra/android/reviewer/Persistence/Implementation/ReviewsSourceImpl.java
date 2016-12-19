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

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.Application.Implementation.Strings;
import com.chdryra.android.reviewer.Application.Interfaces.UserSession;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.VerboseIdableCollection;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Persistence.Interfaces.AuthorsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.MutableRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReferencesRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.RepositoryCallback;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSource;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsSubscriber;

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
    public void subscribe(ReviewsSubscriber subscriber) {
        mReviewsRepo.subscribe(subscriber);
    }

    @Override
    public void unsubscribe(ReviewsSubscriber subscriber) {
        mReviewsRepo.unsubscribe(subscriber);
    }

    @Override
    public ReviewNode asReviewNode(ReviewId id) {
        return newAsyncReview(id);
    }

    @Override
    public ReviewNode asMetaReview(ReviewId id) {
        return newAsyncTree(id);
    }

    @Override
    public ReviewNode getMetaReview(AuthorId id) {
        return mReviewsFactory.createAuthorsTree(id, getReviewsForAuthor(id), mAuthorsRepo,
                Strings.REVIEWS_LIST.REVIEWS_STEM);
    }

    @Override
    public ReviewNode asMetaReview(final VerboseDataReview datum, final String subject) {
        if (!datum.isCollection()) {
            IdableCollection<VerboseDataReview> data = new IdableDataCollection<>();
            data.add(datum);
            return getMetaReview(data, subject);
        } else {
            return getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
                    subject);
        }
//        ReviewId id = getSingleSourceId(datum);
//        if (id != null) {
//            asMetaReviewNullable(id, callback);
//        } else {
//            getMetaReview((VerboseIdableCollection<? extends VerboseDataReview>) datum,
//                    subject, callback);
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
    public ReferencesRepository getReviewsForAuthor(AuthorId authorId) {
        return mReviewsRepo.getReviewsForAuthor(authorId);
    }

    @Override
    public ReviewNode getMetaReview(IdableCollection<?> data, String subject) {
        return newAsyncMetaReview(data, subject);
    }

    @NonNull
    private ReviewNode newAsyncMetaReview(IdableCollection<?> data, final String subject) {
        final NodeAsync asyncNode = newAsyncNode(null);
        getUniqueReviews(data, new RepositoryCallback() {
            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                if (!result.isError()) {
                    Collection<ReviewReference> reviews = result.getReferences();
                    ReviewNode meta = mReviewsFactory.createTree(reviews, subject);
                    result = new RepositoryResult(meta, result.getMessage());
                }

                asyncNode.onCallback(result);
            }
        });

        return asyncNode;
    }

    private ReviewNode newAsyncReview(ReviewId id) {
        final NodeAsync asyncNode = newAsyncNode(id);
        mReviewsRepo.getReference(id, new RepositoryCallback() {

            @Override
            public void onRepositoryCallback(RepositoryResult result) {
                ReviewReference review = result.getReference();
                RepositoryResult repoResult = result;
                if (result.isReference()) {
                    ReviewNode node = mReviewsFactory.createLeafNode(review);
                    repoResult = new RepositoryResult(node, result.getMessage());
                }

                asyncNode.onCallback(repoResult);
            }
        });

        return asyncNode;
    }

    private ReviewNode newAsyncTree(ReviewId id) {
        final NodeAsync asyncNode = newAsyncNode(id);
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

                asyncNode.onCallback(repoResult);
            }
        });

        return asyncNode;
    }

    @NonNull
    private NodeAsync newAsyncNode(@Nullable ReviewId id) {
        Review fetching = mReviewsFactory.createUserReview(Strings.Progress.FETCHING, 0f);

        ReviewReference reference = mReviewsFactory.asReference(fetching);
        ReviewNodeComponent node = mReviewsFactory.createLeafNode(reference);

        return new NodeAsync(id, node);
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
//
//    private void asMetaReviewNullable(ReviewId id, final ReviewsSourceCallback callback) {
//        mReviewsRepo.getReference(id, new RepositoryCallback() {
//
//            @Override
//            public void onRepositoryCallback(RepositoryResult result) {
//                ReviewReference review = result.getReference();
//                RepositoryResult repoResult;
//                if (result.isError() || review == null) {
//                    repoResult = result;
//                } else {
//                    ReviewNode node = mReviewsFactory.createTree(review);
//                    repoResult = new RepositoryResult(node, result.getMessage());
//                }
//
//                callback.onMetaReviewCallback(repoResult);
//            }
//        });
//    }

    private void getUniqueReviews(final IdableCollection<?> data,
                                  final RepositoryCallback callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReference(data.getItem(i).getReviewId(), uniqueCallback);
        }
    }

    private static class NodeAsync extends ReviewTree {
        private ReviewId mId;

        private NodeAsync(@Nullable ReviewId id, ReviewNodeComponent fetchingNode) {
            super(fetchingNode);
            mId = id;
        }

        private void onCallback(RepositoryResult result) {
            if (result.isReviewNode()) setNode(result.getReviewNode());
        }

        @Override
        public ReviewId getReviewId() {
            return mId != null ? mId : super.getReviewId();
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

            if (result.isReference()) {
                mFetched.add(result.getReference());
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
