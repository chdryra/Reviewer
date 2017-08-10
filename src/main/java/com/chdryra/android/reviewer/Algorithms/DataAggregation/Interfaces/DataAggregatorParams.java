/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataAggregation.Interfaces;

import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceDate;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceLocation;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferencePercentage;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataAggregatorParams {
    DifferenceBoolean getSimilarBoolean();

    DifferencePercentage getSimilarPercentage();

    DifferenceDate getSimilarDate();

    DifferenceLocation getSimilarLocation();
}
