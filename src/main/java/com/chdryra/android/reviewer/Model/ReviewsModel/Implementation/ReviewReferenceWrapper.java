/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DatumTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.IdableDataList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataAuthorId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.References.Factories.FactoryReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Implementation.WrapperItemReference;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefCommentList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.RefDataList;
import com.chdryra.android.reviewer.DataDefinitions.References.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceWrapper extends WrapperItemReference<Review> implements ReviewReference {
    private Review mReview;
    private TagsManager mTagsManager;
    private FactoryReference mReferenceFactory;

    public ReviewReferenceWrapper(Review review, TagsManager tagsManager, FactoryReference
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
        return new WrapperItemReference<>(mReview.getCover());
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
