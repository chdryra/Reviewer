/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.Implementation;

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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewItemReference;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewListReference;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReferenceBinder;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.Review;
import com.chdryra.android.reviewer.Model.ReviewsModel.Interfaces.ReviewReference;

/**
 * Created by: Rizwan Choudrey
 * On: 27/06/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ReviewReferenceBasic implements ReviewReference {

    @Override
    public ReviewItemReference<DataImage> getCover() {
        return null;
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
    public void dereference(DereferenceCallback<Review> callback) {

    }

    @Override
    public void bindToValue(ReferenceBinder<Review> binder) {

    }

    @Override
    public void unbindFromValue(ReferenceBinder<Review> binder) {

    }

    @Override
    public boolean isValidReference() {
        return false;
    }

    @Override
    public void invalidate() {

    }

    @Override
    public DataSubject getSubject() {
        return null;
    }

    @Override
    public DataRating getRating() {
        return null;
    }

    @Override
    public DataDate getPublishDate() {
        return null;
    }

    @Override
    public DataAuthorId getAuthorId() {
        return null;
    }

    @Override
    public ReviewId getReviewId() {
        return null;
    }
}
