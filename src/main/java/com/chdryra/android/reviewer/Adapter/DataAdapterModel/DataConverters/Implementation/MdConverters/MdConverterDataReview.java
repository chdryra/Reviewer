package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReview;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.MdData;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterDataReview<T1 extends DataReview, T2 extends MdData, T3 extends MdDataList<T2>>
        extends MdConverterBasic<T1, T2, T3>{

    public MdConverterDataReview(Class<T3> listClass) {
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
