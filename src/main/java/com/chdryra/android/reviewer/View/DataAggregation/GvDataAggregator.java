package com.chdryra.android.reviewer.View.DataAggregation;

import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Factories.FactoryGvData;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonical;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvDataList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvDataType;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Implementation.Data.GvList;
import com.chdryra.android.reviewer.View.Implementation.GvDataModel.Interfaces.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregator<T extends GvData, D1, D2 extends DifferenceLevel<D1>> {
    private DifferenceComparitor<T, D2> mComparitor;
    private D1 mMinimumDifference;
    private CanonicalDatumMaker<T> mCanonical;
    private FactoryGvData mDataFactory;

    //Constructors
    public GvDataAggregator(DifferenceComparitor<T, D2> comparitor,
                            D1 minimumDifference,
                            CanonicalDatumMaker<T> canonical,
                            FactoryGvData dataFactory) {
        mComparitor = comparitor;
        mMinimumDifference = minimumDifference;
        mCanonical = canonical;
        mDataFactory = dataFactory;
    }

    public GvCanonicalCollection<T> aggregate(GvDataList<T> data) {
        GvDataType<T> elementType = data.getGvDataType();

        GvList allocated = new GvList();
        GvCanonicalCollection<T> results = new GvCanonicalCollection<>(data.getGvReviewId(), elementType);
        for (T reference : data) {
            if (allocated.contains(reference)) continue;
            GvDataList<T> similar = mDataFactory.newDataList(elementType, data.getGvReviewId());
            similar.add(reference);
            allocated.add(reference);
            for (T candidate : data) {
                if (allocated.contains(candidate)) continue;
                D2 difference = mComparitor.compare(reference, candidate);
                if (difference.lessThanOrEqualTo(mMinimumDifference)) {
                    similar.add(candidate);
                    allocated.add(candidate);
                }
            }

            results.addCanonnical(new GvCanonical<>(mCanonical.getCanonical(similar), similar));
        }

        return results;
    }
}


