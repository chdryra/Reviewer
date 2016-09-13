/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces;

import android.support.annotation.Nullable;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */

//Really want T2 extends DataReview & T1 but can't do this with generics...
public interface DataConverter<T1, T2 extends HasReviewId, T3 extends IdableList<T2>> {
    T2 convert(T1 datum);

    T2 convert(T1 datum, @Nullable ReviewId reviewId);

    T3 convert(Iterable<? extends T1> data, @Nullable ReviewId reviewId);

    T3 convert(IdableList<? extends T1> data);
}
