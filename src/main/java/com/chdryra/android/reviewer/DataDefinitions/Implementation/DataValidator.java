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
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataSubject;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataTag;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataUrl;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.UserId;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidator {
    public boolean validate(DataAuthor author) {
        return NotNull(author) && validateString(author.getName())
                && validate(author.getUserId());
    }

    public boolean validate(DataComment comment) {
        return NotNull(comment) && validateString(comment.getComment());
    }

    public boolean validate(DataSubject subject) {
        return NotNull(subject) && validateString(subject.getSubject());
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

    public boolean validate(DataCriterion criterion) {
        return NotNull(criterion) && validateString(criterion.getSubject());
    }

    public boolean validate(DataImage image) {
        return NotNull(image) && NotNull(image.getBitmap());
    }

    public boolean validate(DataLocation location) {
        return NotNull(location) && NotNull(location.getLatLng()) && validateString(location
                .getName());
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
        return validateString(reviewId.toString());
    }

    public boolean validate(UserId userId) {
        return !userId.toString().equals(UserId.NULL_ID_STRING) && validateString(userId.toString());
    }

    public boolean NotNull(Object obj) {
        return obj != null;
    }
}
