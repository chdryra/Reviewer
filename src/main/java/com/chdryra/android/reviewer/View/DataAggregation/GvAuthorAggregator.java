package com.chdryra.android.reviewer.View.DataAggregation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.GvConverterAuthors;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces.DataConverter;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces.DataAuthorReview;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.AggregatedCollection;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.AggregatedList;
import com.chdryra.android.reviewer.View.DataAggregation.Interfaces.DataAggregator;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthor;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvAuthorList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvAuthorAggregator {
    private DataAggregator<DataAuthorReview> mAggregator;
    private DataConverter<? super DataAuthorReview, GvAuthor, ? extends GvDataList<GvAuthor>> mConverter;

    public GvAuthorAggregator(DataAggregator<DataAuthorReview> aggregator, GvConverterAuthors
            converter) {
        mAggregator = aggregator;
        mConverter = converter;
    }

    public GvCanonicalCollection<GvAuthor> aggregate(GvAuthorList authors) {
        AggregatedCollection<DataAuthorReview> aggregated = mAggregator.aggregate(authors);
        GvCanonicalCollection<GvAuthor> canonicals
                = new GvCanonicalCollection<>(authors.getGvReviewId(), authors.getGvDataType());

        for(AggregatedList<DataAuthorReview> aggregatedList : aggregated) {
            canonicals.addCanonnical(newGvCanonical(aggregatedList));
        }

        return canonicals;
    }

    @NonNull
    private GvCanonical<GvAuthor> newGvCanonical(AggregatedList<DataAuthorReview> aggregatedList) {
        return new GvCanonical<>(getCanonical(aggregatedList), getAggregated(aggregatedList));
    }

    private GvDataList<GvAuthor> getAggregated(AggregatedList<DataAuthorReview> aggregatedList) {
        return mConverter.convert(aggregatedList.getAggregated());
    }

    private GvAuthor getCanonical(AggregatedList<DataAuthorReview> aggregatedList) {
        return mConverter.convert(aggregatedList.getCanonical(), aggregatedList.getReviewId());
    }
}


