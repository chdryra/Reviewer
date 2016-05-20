/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation.Backend.Implementation;


import android.support.annotation.Nullable;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.mygenerallibrary.OtherUtils.FunctionPointer;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterionReview;
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
import com.chdryra.android.reviewer.Persistence.Interfaces.CallbackRepository;
import com.chdryra.android.reviewer.Persistence.Interfaces.ReviewsRepository;

import java.util.Collection;

/**
 * Created by: Rizwan Choudrey
 * On: 12/04/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewLazy extends ReviewDynamic implements ReviewNode, CallbackRepository {
    private ReviewId mId;
    private DataSubject mSubject;
    private DataRating mRating;
    private DataDateReview mPublishDate;
    private ReviewsRepository mRepo;
    private Review mReview;
    private boolean mFetching = false;

    public ReviewLazy(ReviewId id, DataSubject subject, DataRating rating,
                      DataDateReview publishDate, ReviewsRepository repo) {
        mId = id;
        mSubject = subject;
        mRating = rating;
        mPublishDate = publishDate;
        mRepo = repo;
    }

    @Override
    public void onFetchedFromRepo(@Nullable Review review, CallbackMessage result) {
        if (review != null && !result.isError()) {
            mReview = review;
            notifyReviewObservers();
        }
    }

    @Override
    public void onFetchedFromRepo(Collection<Review> reviews, CallbackMessage result) {
        throw new UnsupportedOperationException("Should never be called!");
    }

    @Override
    public DataSubject getSubject() {
        return mReview != null ? mReview.getSubject() : mSubject;
    }

    @Override
    public DataRating getRating() {
        return mReview != null ? mReview.getRating() : mRating;
    }

    @Override
    public DataAuthorReview getAuthor() {
        return returnData(new DatumAuthorReview(mId, mId.toString(), new DatumAuthorId(mId.toString
                ())),
                new FunctionPointer<Void, DataAuthorReview>() {
                    @Override
                    public DataAuthorReview execute(@Nullable Void data) {
                        return mReview.getAuthor();
                    }
                });
    }

    @Override
    public DataDateReview getPublishDate() {
        return mReview != null ? mReview.getPublishDate() : mPublishDate;
    }

    @Override
    public ReviewNode getTreeRepresentation() {
        return this;
    }

    @Override
    public boolean isRatingAverageOfCriteria() {
        return mReview != null && mReview.isRatingAverageOfCriteria();
    }

    @Override
    public IdableList<? extends DataCriterionReview> getCriteria() {
        return returnData(new IdableDataList<DataCriterionReview>(mId),
                new FunctionPointer<Void, IdableList<? extends DataCriterionReview>>() {
                    @Override
                    public IdableList<? extends DataCriterionReview> execute(@Nullable Void data) {
                        return mReview.getCriteria();
                    }
                });
    }

    @Override
    public IdableList<? extends DataComment> getComments() {
        return returnData(new IdableDataList<DataComment>(mId),
                new FunctionPointer<Void, IdableList<? extends DataComment>>() {
                    @Override
                    public IdableList<? extends DataComment> execute(@Nullable Void data) {
                        return mReview.getComments();
                    }
                });
    }

    @Override
    public IdableList<? extends DataFact> getFacts() {
        return returnData(new IdableDataList<DataFact>(mId),
                new FunctionPointer<Void, IdableList<? extends DataFact>>() {
                    @Override
                    public IdableList<? extends DataFact> execute(@Nullable Void data) {
                        return mReview.getFacts();
                    }
                });
    }

    @Override
    public IdableList<? extends DataImage> getImages() {
        return returnData(new IdableDataList<DataImage>(mId),
                new FunctionPointer<Void, IdableList<? extends DataImage>>() {
                    @Override
                    public IdableList<? extends DataImage> execute(@Nullable Void data) {
                        return mReview.getImages();
                    }
                });
    }

    @Override
    public IdableList<? extends DataImage> getCovers() {
        return returnData(new IdableDataList<DataImage>(mId),
                new FunctionPointer<Void, IdableList<? extends DataImage>>() {
                    @Override
                    public IdableList<? extends DataImage> execute(@Nullable Void data) {
                        return mReview.getCovers();
                    }
                });
    }

    @Override
    public IdableList<? extends DataLocation> getLocations() {
        return returnData(new IdableDataList<DataLocation>(mId),
                new FunctionPointer<Void, IdableList<? extends DataLocation>>() {
                    @Override
                    public IdableList<? extends DataLocation> execute(@Nullable Void data) {
                        return mReview.getLocations();
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

    private <T1, T2 extends T1> T1 returnData(T2 ifNull, FunctionPointer<Void, T1> returnFunction) {
        if (mReview == null) {
            if (!mFetching) {
                mFetching = true;
                mRepo.getReview(mId, this);
            }

            return ifNull;
        } else {
            return returnFunction.execute(null);
        }
    }
}
