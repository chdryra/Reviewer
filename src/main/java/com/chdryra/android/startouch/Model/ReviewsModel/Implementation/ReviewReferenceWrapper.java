/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Model.ReviewsModel.Implementation;

import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.startouch.DataDefinitions.References.Factories.FactoryReferences;
import com.chdryra.android.startouch.DataDefinitions.References.Implementation.StaticItemReference;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.CommentListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.DataListRef;
import com.chdryra.android.startouch.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.startouch.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceWrapper extends StaticItemReference<Review> implements ReviewReference {
    private final Review mReview;
    private final FactoryReferences mReferenceFactory;

    public ReviewReferenceWrapper(Review review,
                                  FactoryReferences referenceFactory) {
        super(review);
        mReview = review;
        mReferenceFactory = referenceFactory;
    }

    @Override
    public void registerObserver(ReviewReferenceObserver observer) {
        //Review is static
    }

    @Override
    public void unregisterObserver(ReviewReferenceObserver observer) {
        //Review is static
    }

    @Override
    public ReviewId getReviewId() {
        return mReview.getReviewId();
    }

    @Override
    public DataSubject getSubject() {
        return mReview.getSubject();
    }

    @Override
    public DataRating getRating() {
        return mReview.getRating();
    }

    @Override
    public DataAuthorId getAuthorId() {
        return mReview.getAuthorId();
    }

    @Override
    public DataDate getPublishDate() {
        return mReview.getPublishDate();
    }

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return new StaticItemReference<>(mReview.getCover());
    }

    @Override
    public DataListRef<DataCriterion> getCriteria() {
        return newWrapper(mReview.getCriteria());
    }

    @Override
    public CommentListRef getComments() {
        return newCommentsWrapper(mReview.getComments());
    }

    @Override
    public DataListRef<DataFact> getFacts() {
        return newWrapper(mReview.getFacts());
    }

    @Override
    public DataListRef<DataImage> getImages() {
        return newWrapper(mReview.getImages());
    }

    @Override
    public DataListRef<DataLocation> getLocations() {
        return newWrapper(mReview.getLocations());
    }

    @Override
    public DataListRef<DataTag> getTags() {
        return newWrapper(mReview.getTags());
    }

    private <T extends HasReviewId> DataListRef<T> newWrapper(IdableList<? extends T> data) {
        return mReferenceFactory.newSuperClassWrapper(data);
    }

    private CommentListRef newCommentsWrapper(IdableList<? extends DataComment> data) {
        return mReferenceFactory.newCommentsWrapper(data);
    }
}
