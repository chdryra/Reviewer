package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.IdableList;
import com.chdryra.android.reviewer.DataDefinitions.Interfaces.ReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */

//Really want T2 extends DataReview & T1 but can't do this with generics...
public interface DataConverter<T1, T2 extends HasReviewId, T3 extends IdableList<T2>> {
    T2 convert(T1 datum);

    T2 convert(T1 datum, ReviewId reviewId);

    T3 convert(Iterable<? extends T1> data, ReviewId reviewId);

    T3 convert(IdableList<? extends T1> data);
}
