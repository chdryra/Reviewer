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
public interface DataReviewInfo extends HasReviewId {
    String DATUM_NAME = "review";

    @Override
    ReviewId getReviewId();

    DataSubject getSubject();

    DataRating getRating();

    DataDate getPublishDate();

    DataAuthorId getAuthorId();
}
