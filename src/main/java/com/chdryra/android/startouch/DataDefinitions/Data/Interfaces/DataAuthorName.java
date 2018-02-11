/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.DataDefinitions.Data.Interfaces;

import com.chdryra.android.startouch.DataDefinitions.Data.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAuthorName extends HasReviewId, AuthorName, DataAuthorId {
    @Override
    String getName();

    @Override
    AuthorId getAuthorId();

    @Override
    boolean hasData(DataValidator dataValidator);

    @Override
    ReviewId getReviewId();
}
