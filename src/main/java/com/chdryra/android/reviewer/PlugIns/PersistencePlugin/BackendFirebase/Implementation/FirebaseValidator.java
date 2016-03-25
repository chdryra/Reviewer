/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.PersistencePlugin.BackendFirebase.Implementation;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FirebaseValidator {

    public boolean isValid(FbReview review) {
        Rating rating = review.getRating();
        return validateString(review.getReviewId()) && validateString(review.getSubject())
                && isValid(review.getRating()) && isValid(review.getAuthor());
    }

    private boolean isValid(Author author) {
        return validateString(author.getName()) && validateString(author.getUserId());
    }

    public boolean isValid(Rating rating) {
        return rating.getRatingWeight() > 0;
    }

    private boolean validateString(String string) {
        return string != null && string.length() > 0;
    }
}
