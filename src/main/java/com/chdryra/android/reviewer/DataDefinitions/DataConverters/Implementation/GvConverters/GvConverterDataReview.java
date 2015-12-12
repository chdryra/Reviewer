package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData
        .GvDataListImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterDataReview <T1 extends HasReviewId, T2 extends GvData, T3 extends GvDataListImpl<T2>>
        extends GvConverterBasic<T1, T2, T3>{

    public GvConverterDataReview(Class<T3> listClass) {
        super(listClass);
    }

    @Override
    public T2 convert(T1 datum, ReviewId reviewId) {
        if(!datum.getReviewId().equals(reviewId)) {
            throw new IllegalArgumentException("reviewId must equal datum's getReviewId!");
        }

        return convert(datum);
    }

    @Override
    public abstract T2 convert(T1 datum);
}
