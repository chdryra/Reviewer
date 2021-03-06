/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.PersistencePlugin.Implementation
        .Backend.Implementation;


import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.AuthorIdParcelable;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumRating;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumReviewId;
import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DatumSubject;

/**
 * Created by: Rizwan Choudrey
 * On: 25/03/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class BackendValidator {
    private final DataValidator mValidator;

    public BackendValidator(DataValidator validator) {
        mValidator = validator;
    }

    public boolean isValid(ReviewDb review) {
        DatumReviewId reviewId = new DatumReviewId(review.getReviewId());
        DatumSubject subject = new DatumSubject(reviewId, review.getSubject());
        DatumRating rating = new DatumRating(reviewId, (float) review.getRating().getRating(),
                (int) review
                .getRating().getRatingWeight());

        return isIdValid(review)
                && mValidator.validate(subject)
                && mValidator.validate(rating) && isValid(review.getAuthorId()) && review.getTags
                ().size() > 0;
    }

    private boolean isIdValid(ReviewDb review) {
        DatumReviewId reviewId = new DatumReviewId(review.getReviewId());

        return mValidator.validate(reviewId);
    }

    private boolean isValid(String authorId) {
        return authorId != null && mValidator.validate(new AuthorIdParcelable(authorId));
    }
}
