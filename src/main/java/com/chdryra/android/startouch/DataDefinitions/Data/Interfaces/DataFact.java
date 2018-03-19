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
 * On: 09/12/2014
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataFact extends HasReviewId, Validatable {
    String TYPE_NAME = "fact";
    String LABEL = "label";
    String VALUE = "value";

    String getLabel();

    String getValue();

    boolean isUrl();

    @Override
    ReviewId getReviewId();

    @Override
    boolean hasData(DataValidator dataValidator);
}
