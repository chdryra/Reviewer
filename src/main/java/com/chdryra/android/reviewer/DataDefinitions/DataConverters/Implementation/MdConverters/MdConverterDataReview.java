package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.DataReviewIdable;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterDataReview<T1 extends DataReviewIdable, T2 extends DataReviewIdable>
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
