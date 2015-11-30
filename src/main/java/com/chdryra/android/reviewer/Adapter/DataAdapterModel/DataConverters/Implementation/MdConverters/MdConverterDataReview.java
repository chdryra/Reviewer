package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Interfaces.MdData;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterDataReview<T1 extends DataReviewIdable, T2 extends MdData>
        extends MdConverterBasic<T1, T2>{

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
