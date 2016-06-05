/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Implementation;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataAuthor;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataComment;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataCriterion;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataDate;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataFact;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataImage;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataLocation;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataRating;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.AuthorId;
import com.google.android.gms.maps.model.LatLng;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidator {
    public boolean validate(DataAuthor author) {
        return NotNull(author) && validateString(author.getName())
                && validate(author.getAuthorId());
    }

    public boolean validate(DataComment comment) {
        return NotNull(comment) && validateString(comment.getComment());
    }

    public boolean validate(DataSubject subject) {
        return NotNull(subject) && validateString(subject.getSubject());
    }

    public boolean validate(DataRating rating) {
        return NotNull(rating) && rating.getRating() >= 0f && rating.getRatingWeight() > 0;
    }

    public boolean validate(DataCriterion criterion) {
        return NotNull(criterion) && criterion.getRating() >= 0f && validateString(criterion.getSubject());
    }

    public boolean validate(DataTag tag) {
        return NotNull(tag) && validateString(tag.getTag());
    }

    public boolean validate(DataFact fact) {
        if (!NotNull(fact)) return false;

        if (fact.isUrl()) {
            return validateUrl((DataUrl) fact);
        } else {
            return validateString(fact.getLabel()) && validateString(fact.getValue());

        }
    }

    public boolean validate(DataImage image) {
        return NotNull(image) && NotNull(image.getBitmap());
    }

    public boolean validate(DataLocation location) {
        LatLng latLng = location.getLatLng();
        return NotNull(location) && validateString(location.getName()) && NotNull(latLng)
                && latLng.latitude >= -90. && latLng.latitude <= 90.
                && latLng.longitude >= -180. && latLng.longitude <= 180.;
    }

    public boolean validate(DataDate date) {
        return date.getTime() > 0;
    }

    public boolean validateUrl(DataUrl url) {
        return NotNull(url) && NotNull(url.getUrl()) && validateString(url.getLabel());
    }

    public boolean validateString(String string) {
        return NotNull(string) && string.length() > 0;
    }

    public boolean validate(ReviewId reviewId) {
        return ReviewStamp.checkId(reviewId);
    }

    public boolean validate(AuthorId authorId) {
        return !authorId.toString().equals(AuthorId.NULL_ID_STRING) && validateString(authorId.toString());
    }

    public boolean NotNull(Object obj) {
        return obj != null;
    }
}
