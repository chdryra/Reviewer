package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdDataList;
import com.chdryra.android.reviewer.Model.Implementation.ReviewsModel.Implementation.MdReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class MdConverterBasic<T1, T2 extends HasReviewId>
        implements DataConverter<T1, T2, MdDataList<T2>> {

    @Override
    public abstract T2 convert(T1 datum);

    @Override
    public MdDataList<T2> convert(Iterable<? extends T1> data, ReviewId reviewId) {
        MdDataList<T2> list = new MdDataList<>(getMdReviewId(reviewId));
        for(T1 datum : data) {
            list.add(convert(datum));
        }

        return list;
    }

    @Override
    public MdDataList<T2> convert(IdableList<? extends T1> data) {
        MdDataList<T2> list = new MdDataList<>(getMdReviewId(data.getReviewId()));
        for(T1 datum : data) {
            list.add(convert(datum));
        }

        return list;
    }

    @NonNull
    private MdReviewId getMdReviewId(ReviewId reviewId) {
        return new MdReviewId(reviewId);
    }
}
