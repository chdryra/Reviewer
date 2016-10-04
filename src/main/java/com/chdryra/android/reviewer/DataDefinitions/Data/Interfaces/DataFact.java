/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Data.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataFact extends HasReviewId, Validatable{
    String getLabel();

    String getValue();

    boolean isUrl();

    @Override
    ReviewId getReviewId();

    @Override
    boolean hasData(DataValidator dataValidator);
}