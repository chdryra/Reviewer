/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryMdReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceWrapper extends StaticItemReference<Review> implements ReviewReference {
    private Review mReview;
    private TagsManager mTagsManager;
    private FactoryMdReference mReferenceFactory;

    public ReviewReferenceWrapper(Review review, TagsManager tagsManager, FactoryMdReference
            referenceFactory) {
        super(review);
        mReview = review;
        mTagsManager = tagsManager;
        mReferenceFactory = referenceFactory;
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
    public RefDataList<DataCriterion> getCriteria() {
        return newWrapper(mReview.getCriteria());
    }

    @Override
    public RefCommentList getComments() {
        return newCommentsWrapper(mReview.getComments());
    }

    @Override
    public RefDataList<DataFact> getFacts() {
        return newWrapper(mReview.getFacts());
    }

    @Override
    public RefDataList<DataImage> getImages() {
        return newWrapper(mReview.getImages());
    }

    @Override
    public RefDataList<DataLocation> getLocations() {
        return newWrapper(mReview.getLocations());
    }

    @Override
    public RefDataList<DataTag> getTags() {
        IdableList<DataTag> tags = new IdableDataList<>(getReviewId());
        for(ItemTag tag : mTagsManager.getTags(getReviewId().toString())) {
            tags.add(new DatumTag(getReviewId(), tag.getTag()));
        }
        return mReferenceFactory.newWrapper(tags);
    }

    private <T extends HasReviewId> RefDataList<T> newWrapper(IdableList<? extends T> data) {
        return mReferenceFactory.newSuperClassWrapper(data);
    }

    private RefCommentList newCommentsWrapper(IdableList<? extends DataComment> data) {
        return mReferenceFactory.newCommentsWrapper(data);
    }
}
