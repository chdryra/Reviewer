/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

import com.chdryra.android.mygenerallibrary.AsyncUtils.CallbackMessage;
import com.chdryra.android.reviewer.DataDefinitions.Implementation.DatumSize;
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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSize;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Factories.FactoryBinders;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTag;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.ItemTagCollection;
import com.chdryra.android.reviewer.Model.TagsModel.Interfaces.TagsManager;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class ReviewReferenceWrapper implements ReviewReference {
    private static final CallbackMessage OK = CallbackMessage.ok();
    private Review mReview;
    private TagsManager mTagsManager;

    public ReviewReferenceWrapper(Review review,
                                  TagsManager tagsManager,
                                  FactoryBinders bindersFactory) {
        mReview = review;
        mTagsManager = tagsManager;
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
        return new StaticItemReference<>(getReviewId(), mReview.getCover());
    }

    @Override
    public ReviewListReference<DataCriterion> getCriteria() {
        return null;
    }

    @Override
    public ReviewListReference<DataComment> getComments() {
        return null;
    }

    @Override
    public ReviewListReference<DataFact> getFacts() {
        return null;
    }

    @Override
    public ReviewListReference<DataImage> getImages() {
        return null;
    }

    @Override
    public ReviewListReference<DataLocation> getLocations() {
        return null;
    }

    @Override
    public ReviewListReference<DataTag> getTags() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getCriteriaSize() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getCommentsSize() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getFactsSize() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getImagesSize() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getLocationsSize() {
        return null;
    }

    @Override
    public ReviewItemReference<DataSize> getTagsSize() {
        return null;
    }

    @Override
    public void bindToValue(ReferenceBinder<Review> binder) {

    }

    @Override
    public void unbindFromValue(ReferenceBinder<Review> binder) {

    }

    @Override
    public void invalidate() {

    }

    @Override
    public void getData(TagsCallback callback) {
        ItemTagCollection tagCollection = mTagsManager.getTags(getReviewId().toString());
        IdableList<DataTag> tags = new IdableDataList<>(getReviewId());
        for (ItemTag tag : tagCollection) {
            tags.add(new DatumTag(getReviewId(), tag.getTag()));
        }
        callback.onTags(tags, OK);
    }


    @Override
    public void dereference(DereferenceCallback<Review> callback) {
        callback.onDereferenced(mReview, OK);
    }

    @Override
    public boolean isValidReference() {
        return mReview != null;
    }
}
