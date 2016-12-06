/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 05/12/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class RatingDefinition {
    public static final float MIN_RATING = 0f;
    public static final float MAX_RATING = 5f;

    public static void throwIfOutOfRange(float rating){
        if(!isRating(rating)) throw new IllegalArgumentException("Rating should be between 0-5");
    }

    public static boolean isRating(float rating) {
        return rating >= MIN_RATING && rating <= MAX_RATING;
    }
}
