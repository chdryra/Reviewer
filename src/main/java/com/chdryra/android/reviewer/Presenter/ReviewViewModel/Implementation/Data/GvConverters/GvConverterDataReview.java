/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataListParcelable;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterDataReview <T1 extends HasReviewId, T2 extends GvData, T3 extends GvDataListParcelable<T2>>
        extends GvConverterBasic<T1, T2, T3>{

    public GvConverterDataReview(Class<T3> listClass) {
        super(listClass);
    }

    @Override
    public abstract T2 convert(T1 datum, ReviewId reviewId);

    @Override
    public T2 convert(T1 datum) {
        return convert(datum, datum.getReviewId());
    };
}
