/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Persistence.Implementation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.chdryra.android.corelibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.corelibrary.ReferenceModel.Implementation.SizeReferencer;
import com.chdryra.android.startouch.Application.Implementation.Strings;
import com.chdryra.android.startouch.Authentication.Interfaces.UserSession;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.IdableDataCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.AuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableCollection;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.VerboseDataReview;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.VerboseIdableCollection;
import com.chdryra.android.startouch.Model.ReviewsModel.Factories.FactoryReviews;
import com.chdryra.android.startouch.Model.ReviewsModel.Implementation.ReviewTree;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewNodeComponent;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.startouch.Persistence.Interfaces.AuthorsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.RepoCallback;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewCollection;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewNodeRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepo;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoReadable;
import com.chdryra.android.startouch.Persistence.Interfaces.ReviewsRepoWriteable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewNodeRepoImpl extends RepoReadableBasic implements ReviewNodeRepo {
    private final ReviewsRepo mReviewsRepo;
    private final AuthorsRepo mAuthorsRepo;
    private final FactoryReviews mReviewsFactory;

    public ReviewNodeRepoImpl(ReviewsRepo reviewsRepo,
                              AuthorsRepo authorsRepo,
                              FactoryReviews reviewsFactory,
                              ReviewDereferencer dereferencer,
                              SizeReferencer sizeReferencer) {
        super(dereferencer, sizeReferencer);
        mReviewsRepo = reviewsRepo;
        mAuthorsRepo = authorsRepo;
        mReviewsFactory = reviewsFactory;
    }

    @Override
    public void subscribe(ItemSubscriber<ReviewReference> binder) {
        mReviewsRepo.subscribe(binder);
    }

    @Override
    public void unsubscribe(ItemSubscriber<ReviewReference> binder) {
        mReviewsRepo.unsubscribe(binder);
    }

    @Override
    protected void doDereferencing(DereferenceCallback<List<ReviewReference>> callback) {
        mReviewsRepo.dereference(callback);
    }

    @Override
    public ReviewNode asMetaReview(ReviewId id) {
        return newAsyncTree(id);
    }

    @Override
    public ReviewNode getMetaReview(AuthorId id) {
        return getMetaReview(getRepoForAuthor(id), id, Strings.ReviewsList.REVIEWS);
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
    }

    @Override
    public ReviewsRepoWriteable getRepoForUser(UserSession session) {
        return mReviewsRepo.getRepoForUser(session);
    }

    @Override
    public ReviewCollection getCollectionForAuthor(AuthorId authorId, String name) {
        return mReviewsRepo.getCollectionForAuthor(authorId, name);
    }

    @Override
    public void getReference(ReviewId reviewId, RepoCallback callback) {
        mReviewsRepo.getReference(reviewId, callback);
    }

    @Override
    public ReviewsRepoReadable getRepoForAuthor(AuthorId authorId) {
        return mReviewsRepo.getRepoForAuthor(authorId);
    }

    @Override
    public ReviewNode getMetaReview(IdableCollection<?> data, String subject) {
        return newAsyncMetaReview(data, subject);
    }

    @Override
    public ReviewNode getMetaReview(ReviewsRepoReadable repo, AuthorId owner, String subject) {
        return mReviewsFactory.createTree(repo, mAuthorsRepo.getAuthor(owner), subject);
    }

    @NonNull
    private ReviewNode newAsyncMetaReview(IdableCollection<?> data, final String subject) {
        final NodeAsync asyncNode = newAsyncNode(null);
        getUniqueReviews(data, new RepoCallback() {
            @Override
            public void onRepoCallback(RepoResult result) {
                if (!result.isError()) {
                    Collection<ReviewReference> reviews = result.getReferences();
                    ReviewNode meta = mReviewsFactory.createTree(reviews, subject);
                    result = new RepoResult(meta, result.getMessage());
                }

                asyncNode.onCallback(result);
            }
        });

        return asyncNode;
    }

    private ReviewNode newAsyncTree(ReviewId id) {
        final NodeAsync asyncNode = newAsyncNode(id);
        mReviewsRepo.getReference(id, new RepoCallback() {

            @Override
            public void onRepoCallback(RepoResult result) {
                ReviewReference review = result.getReference();
                RepoResult repoResult;
                if (result.isError() || review == null) {
                    repoResult = result;
                } else {
                    ReviewNode node = mReviewsFactory.createTree(review);
                    repoResult = new RepoResult(node, result.getMessage());
                }

                asyncNode.onCallback(repoResult);
            }
        });

        return asyncNode;
    }

    @NonNull
    private NodeAsync newAsyncNode(@Nullable ReviewId id) {
        Review fetching = mReviewsFactory.createPlaceholderReview(id, Strings.Progress.FETCHING);

        ReviewReference reference = mReviewsFactory.asReference(fetching);
        ReviewNodeComponent node = mReviewsFactory.createLeafNode(reference);

        return new NodeAsync(id, node);
    }

//
//    @Nullable
//    private ReviewId getSingleSourceId(VerboseDataReview datum) {
//        ReviewId id = datum.getAuthorId();
//        if (datum.isCollection() && datum.hasElements()) {
//            VerboseIdableCollection<? extends VerboseDataReview> data =
//                    (VerboseIdableCollection<? extends VerboseDataReview>) datum;
//            id = data.getItem(0).getAuthorId();
//            for (VerboseDataReview element : data) {
//                if (!element.getAuthorId().equals(id)) {
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
                                  final RepoCallback callback) {
        UniqueCallback uniqueCallback = new UniqueCallback(data.size(), callback);
        for (int i = 0; i < data.size(); ++i) {
            getReference(data.get(i).getReviewId(), uniqueCallback);
        }
    }

    private static class NodeAsync extends ReviewTree {
        private ReviewId mId;

        private NodeAsync(@Nullable ReviewId id, ReviewNodeComponent fetchingNode) {
            super(fetchingNode);
            mId = id;
        }

        @Override
        public ReviewId getReviewId() {
            return mId != null ? mId : super.getReviewId();
        }

        private void onCallback(RepoResult result) {
            if (result.isReviewNode()) setNode(result.getReviewNode());
        }
    }

    private class UniqueCallback implements RepoCallback {
        private final ArrayList<CallbackMessage> mErrors;
        private final RepoCallback mFinalCallback;
        private final Set<ReviewReference> mFetched;
        private final int mMaxReviews;
        private int mCurrentIndex;

        private UniqueCallback(int maxReviews, RepoCallback finalCallback) {
            mMaxReviews = maxReviews;
            mFinalCallback = finalCallback;
            mCurrentIndex = 0;
            mErrors = new ArrayList<>();
            mFetched = new HashSet<>();
        }

        @Override
        public void onRepoCallback(RepoResult result) {
            mCurrentIndex++;

            if (result.isReference()) {
                mFetched.add(result.getReference());
            } else {
                mErrors.add(result.getMessage());
            }

            if (mCurrentIndex == mMaxReviews) {
                CallbackMessage message = mErrors.size() > 0 ?
                        CallbackMessage.error("Errors fetching some " + Strings.REVIEWS)
                        : CallbackMessage.ok(mMaxReviews + " " + Strings.REVIEWS + " fetched");
                mFinalCallback.onRepoCallback(new RepoResult(mFetched, message));
            }
        }
    }
}
