package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataReviewIdable;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.IdableList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.AggregatedList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class AggregatedListConverter<T1 extends DataReviewIdable, T2 extends DataReviewIdable> {
    private DataConverter<T1, T2, ? extends IdableList<T2>> mConverter;

    public AggregatedListConverter(DataConverter<T1, T2, ? extends IdableList<T2>> converter) {
        mConverter = converter;
    }

    public IdableList<T2> getAggregated(AggregatedList<T1> aggregatedList) {
        return mConverter.convert(aggregatedList.getAggregated());
    }

    public T2 getCanonical(AggregatedList<T1> aggregatedList) {
        return mConverter.convert(aggregatedList.getCanonical(), aggregatedList.getReviewId());
    }
}


