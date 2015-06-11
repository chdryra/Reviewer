/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 16 January, 2015
 */

package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 16/01/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataValidator {
    public static boolean validate(DataComment comment) {
        return NotNull(comment) && validateString(comment.getComment());
    }

    public static boolean validate(DataFact fact) {
        if (!NotNull(fact)) return false;

        if (fact.isUrl()) {
            return validateUrl((DataUrl) fact);
        } else {
            return validateString(fact.getLabel()) && validateString(fact.getValue());

        }
    }

    public static boolean validate(DataImage image) {
        return NotNull(image) && NotNull(image.getBitmap());
    }

    public static boolean validate(DataLocation location) {
        return NotNull(location) && NotNull(location.getLatLng()) && validateString(location
                .getName());
    }

    public static boolean validateUrl(DataUrl url) {
        return NotNull(url) && NotNull(url.getUrl()) && validateString(url.getLabel());
    }

    public static boolean validateString(String string) {
        return NotNull(string) && string.length() > 0;
    }

    public static boolean NotNull(Object obj) {
        return obj != null;
    }
}
