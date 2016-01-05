package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Factories;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation
        .ComparitorLevenshteinDistance;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DataAggregatorParamsImpl;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceBoolean;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceDate;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceFloat;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferenceLocation;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation.DifferencePercentage;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ComparitorString;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregatorParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataAggregatorParams {
    public DataAggregatorParams getDefaultParams() {
        DifferenceBoolean bool = new DifferenceBoolean(false);
        DifferencePercentage percentage = new DifferencePercentage(0);
        DifferenceDate date = new DifferenceDate(DifferenceDate.DateBucket.DAY);
        DifferenceFloat tenMetres = new DifferenceFloat(10f);
        DifferenceLocation location = new DifferenceLocation(tenMetres, percentage);
        ComparitorString comparitor = new ComparitorLevenshteinDistance();

        return new DataAggregatorParamsImpl(bool, percentage, date, location, comparitor);
    }
}
