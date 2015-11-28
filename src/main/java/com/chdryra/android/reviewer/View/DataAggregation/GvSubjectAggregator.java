package com.chdryra.android.reviewer.View.DataAggregation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataSubject;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.AggregatedCollection;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvSubject;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvSubjectAggregator {
    private DataAggregator<DataSubject> mAggregator;
    private DataConverter<? super DataSubject, GvSubject, ? extends GvDataList<GvSubject>> mConverter;

    public GvSubjectAggregator(DataAggregator<DataSubject> aggregator,
                               DataConverter<? super DataSubject, GvSubject, ? extends GvDataList<GvSubject>> converter) {
        mAggregator = aggregator;
        mConverter = converter;
    }

    public GvCanonicalCollection<GvSubject> aggregate(GvDataList<GvSubject> data) {
        AggregatedCollection<DataSubject> aggregated = mAggregator.aggregate(data);
        GvCanonicalCollection<GvSubject> canonicals
                = new GvCanonicalCollection<>(data.getGvReviewId(), data.getGvDataType());

        for(AggregatedList<DataSubject> aggregatedList : aggregated) {
            canonicals.addCanonnical(newGvCanonical(aggregatedList));
        }

        return canonicals;
    }

    @NonNull
    private GvCanonical<GvSubject> newGvCanonical(AggregatedList<DataSubject> aggregatedList) {
        return new GvCanonical<>(getCanonical(aggregatedList), getAggregated(aggregatedList));
    }

    private GvDataList<GvSubject> getAggregated(AggregatedList<DataSubject> aggregatedList) {
        return mConverter.convert(aggregatedList.getAggregated());
    }

    private GvSubject getCanonical(AggregatedList<DataSubject> aggregatedList) {
        return mConverter.convert(aggregatedList.getCanonical(), aggregatedList.getReviewId());
    }
}


