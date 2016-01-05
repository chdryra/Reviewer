package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ComparitorString;
import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.DataAggregatorParams;

/**
 * Created by: Rizwan Choudrey
 * On: 05/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorParamsImpl implements DataAggregatorParams{
    private DifferenceBoolean mBoolean;
    private DifferencePercentage mPercentage;
    private DifferenceDate mDate;
    private DifferenceLocation mLocation;
    private ComparitorString mComparitor;

    public DataAggregatorParamsImpl(DifferenceBoolean aBoolean, DifferencePercentage percentage,
                                    DifferenceDate date, DifferenceLocation location,
                                    ComparitorString comparitor) {
        mBoolean = aBoolean;
        mPercentage = percentage;
        mDate = date;
        mLocation = location;
        mComparitor = comparitor;
    }

    @Override
    public DifferenceBoolean getSimilarBoolean() {
        return mBoolean;
    }

    @Override
    public DifferencePercentage getSimilarPercentage() {
        return mPercentage;
    }

    @Override
    public DifferenceDate getSimilarDate() {
        return mDate;
    }

    @Override
    public DifferenceLocation getSimilarLocation() {
        return mLocation;
    }

    @Override
    public ComparitorString getStringComparitor() {
        return mComparitor;
    }
}
