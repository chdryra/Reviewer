/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import com.chdryra.android.reviewer.Interfaces.Data.DataAuthor;
import com.chdryra.android.reviewer.Interfaces.Data.DataComment;
import com.chdryra.android.reviewer.Interfaces.Data.DataDate;
import com.chdryra.android.reviewer.Interfaces.Data.DataFact;
import com.chdryra.android.reviewer.Interfaces.Data.DataImage;
import com.chdryra.android.reviewer.Interfaces.Data.DataLocation;
import com.chdryra.android.reviewer.Interfaces.Data.DataUrl;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidator {

    public boolean validate(DataAuthor author) {
        return NotNull(author) && validateString(author.getName()) && validateString(author.getUserId());
    }

    public boolean validate(DataComment comment) {
        return NotNull(comment) && validateString(comment.getComment());
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

    public boolean validateReviewId(String reviewId) {
        return validateString(reviewId);
    }

    public boolean validateUserId(String userId) {
        return validateString(userId);
    }

    public boolean NotNull(Object obj) {
        return obj != null;
    }
}
