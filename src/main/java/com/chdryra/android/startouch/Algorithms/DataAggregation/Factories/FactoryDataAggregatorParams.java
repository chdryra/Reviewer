/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.Algorithms.DataAggregation.Factories;

import com.chdryra.android.startouch.Algorithms.DataAggregation.Implementation.DataAggregatorParamsImpl;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceDate;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceFloat;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceLocation;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferencePercentage;
import com.chdryra.android.startouch.Algorithms.DataAggregation.Interfaces.DataAggregatorParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataAggregatorParams {
    public DataAggregatorParams getDefaultParams() {
        DifferenceBoolean bool = new DifferenceBoolean(false);
        DifferencePercentage percentage = new DifferencePercentage(0.);
        DifferenceDate date = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceFloat tenMetres = new DifferenceFloat(10f);
        DifferenceLocation location = new DifferenceLocation(tenMetres, percentage);

        return new DataAggregatorParamsImpl(bool, percentage, date, location);
    }
}
