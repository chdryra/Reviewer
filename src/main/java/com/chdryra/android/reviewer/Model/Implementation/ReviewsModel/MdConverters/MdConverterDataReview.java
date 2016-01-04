package com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.MdConverters;

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
    public T2 convert(T1 datum, ReviewId reviewId) {
        if(!datum.getReviewId().equals(reviewId)) {
            throw new IllegalArgumentException("ReviewId must equal datum's getReviewId!");
        }

        return convert(datum);
    }

    @Override
    public abstract T2 convert(T1 datum);
}
