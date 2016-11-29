/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataCriterion extends HasReviewId, Validatable {
    String DATUM_NAME = "criterion";
    String DATA_NAME = "criteria";
    String RATING = DataRating.DATUM_NAME;
    String SUBJECT = DataSubject.DATUM_NAME;

    String getSubject();

    float getRating();

    @Override
    ReviewId getReviewId();
}
