/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Model.ReviewsModel.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterDataReview<T1 extends HasReviewId, T2 extends HasReviewId>
        extends MdConverterBasic<T1, T2>{

    @Override
    public abstract T2 convert(T1 datum, ReviewId reviewId);

    @Override
    public T2 convert(T1 datum) {
        return convert(datum, datum.getReviewId());
    }
}
