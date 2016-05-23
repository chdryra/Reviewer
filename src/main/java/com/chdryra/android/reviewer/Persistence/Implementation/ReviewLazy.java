/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Persistence.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.OtherUtils.FunctionPointer;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation.BackendReviewsRepo;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDateReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.ReviewsModel.Implementation.ReviewDynamic;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewNode;
import com.chdryra.android.reviewer.Model.TreeMethods.Interfaces.VisitorReviewNode;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsCache;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLazy extends ReviewDynamic implements ReviewNode, ReviewsRepository.RepositoryCallback {
    private final ReviewId mId;
    private final DataSubject mSubject;
    private final DataRating mRating;
    private final DataDateReview mPublishDate;
    private final BackendReviewsRepo mRepo;
    private final ReviewsCache mCache;

    private boolean mFetching = false;

    public ReviewLazy(ReviewId id, DataSubject subject, DataRating rating,
                      DataDateReview publishDate, BackendReviewsRepo repo, ReviewsCache cache) {
        mId = id;
        mSubject = subject;
        mRating = rating;
        mPublishDate = publishDate;
        mRepo = repo;
        mCache = cache;
    }

    @Override
    public void onRepositoryCallback(RepositoryResult result) {
        Review review = result.getReview();
        if (!result.isError() && review != null) {
            mCache.add(review);
            notifyReviewObservers();
        }
        mFetching = false;
    }

    @Override
    public DataSubject getSubject() {
        return mSubject;
    }

    @Override
    public DataRating getRating() {
        return mRating;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return returnData(new DatumAuthorReview(mId, "loading",
                new DatumAuthorId(mId.toString())),
                new FunctionPointer<Review, DataAuthorReview>() {
                    @Override
                    public DataAuthorReview execute(Review review) {
                        return review.getAuthor();
                    }
                });
    }

    @Override
    public DataDateReview getPublishDate() {
        return mPublishDate;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return returnData(false, new FunctionPointer<Review, Boolean>() {
            @Override
            public Boolean execute(Review data) {
                return getReview().isRatingAverageOfCriteria();
            }
        });
    }

    @Override
    public IdableList<? extends DataCriterion> getCriteria() {
        return returnData(new IdableDataList<DataCriterion>(mId),
                new FunctionPointer<Review, IdableList<? extends DataCriterion>>() {
                    @Override
                    public IdableList<? extends DataCriterion> execute(Review review) {
                        return review.getCriteria();
                    }
                });
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return returnData(new IdableDataList<DataComment>(mId),
                new FunctionPointer<Review, IdableList<? extends DataComment>>() {
                    @Override
                    public IdableList<? extends DataComment> execute(Review review) {
                        return review.getComments();
                    }
                });
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return returnData(new IdableDataList<DataFact>(mId),
                new FunctionPointer<Review, IdableList<? extends DataFact>>() {
                    @Override
                    public IdableList<? extends DataFact> execute(Review review) {
                        return review.getFacts();
                    }
                });
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return returnData(new IdableDataList<DataImage>(mId),
                new FunctionPointer<Review, IdableList<? extends DataImage>>() {
                    @Override
                    public IdableList<? extends DataImage> execute(Review review) {
                        return review.getImages();
                    }
                });
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return returnData(new IdableDataList<DataImage>(mId),
                new FunctionPointer<Review, IdableList<? extends DataImage>>() {
                    @Override
                    public IdableList<? extends DataImage> execute(Review review) {
                        return review.getCovers();
                    }
                });
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return returnData(new IdableDataList<DataLocation>(mId),
                new FunctionPointer<Review, IdableList<? extends DataLocation>>() {
                    @Override
                    public IdableList<? extends DataLocation> execute(Review review) {
                        return review.getLocations();
                    }
                });

    }

    @Override
    public ReviewId getReviewId() {
        return mId;
    }

    @Override
    public void registerNodeObserver(NodeObserver observer) {
    }

    @Override
    public void unregisterNodeObserver(NodeObserver observer) {
    }

    @Override
    public Review getReview() {
        return this;
    }

    @Nullable
    @Override
    public ReviewNode getParent() {
        return null;
    }

    @Override
    public ReviewNode getRoot() {
        return this;
    }

    @Override
    public boolean isExpandable() {
        return false;
    }

    @Override
    public ReviewNode expand() {
        return this;
    }

    @Override
    public IdableList<ReviewNode> getChildren() {
        return new IdableDataList<>(mId);
    }

    @Nullable
    @Override
    public ReviewNode getChild(ReviewId reviewId) {
        return null;
    }

    @Override
    public boolean hasChild(ReviewId reviewId) {
        return false;
    }

    @Override
    public void acceptVisitor(VisitorReviewNode visitor) {

    }

    @Override
    public boolean isRatingAverageOfChildren() {
        return false;
    }

    private <T1, T2 extends T1> T1 returnData(T2 ifNull, FunctionPointer<Review, T1> returnFunction) {
        if (mCache.contains(mId)) {
            Review data = mCache.get(mId);
            return returnFunction.execute(data);
        } else if (!mFetching) {
            mFetching = true;
            mRepo.getReviewActual(mId, this);
        }

        return ifNull;
    }
}
