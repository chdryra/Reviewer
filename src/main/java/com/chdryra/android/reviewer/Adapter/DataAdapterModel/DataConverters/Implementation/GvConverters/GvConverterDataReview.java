package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataListImpl;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class GvConverterDataReview <T1 extends DataReviewIdable, T2 extends GvData, T3 extends GvDataListImpl<T2>>
        extends GvConverterBasic<T1, T2, T3>{

    public GvConverterDataReview(Class<T3> listClass) {
        super(listClass);
    }

    @Override
    public T2 convert(T1 datum, String reviewId) {
        if(!datum.getReviewId().equals(reviewId)) {
            throw new IllegalArgumentException("reviewId must equal datum's getReviewId!");
        }

        return convert(datum);
    }

    @Override
    public abstract T2 convert(T1 datum);
}
