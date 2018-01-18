/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

import com.chdryra.android.reviewer.Application.Implementation.Strings;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataReviewInfo extends HasReviewId, HasAuthorId {
    String TYPE_NAME = Strings.REVIEW;

    DataSubject getSubject();

    DataRating getRating();

    DataDate getPublishDate();

    @Override
    ReviewId getReviewId();

    @Override
    DataAuthorId getAuthorId();
}
