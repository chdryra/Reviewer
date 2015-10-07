package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalCollection;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregater<T extends GvData, D1, D2 extends DifferenceLevel<D1>> {
    private DifferenceComparitor<T, D2> mComparitor;
    private D1 mMinimumDifference;
    private CanonicalDatumMaker<T> mCanonical;

    //Constructors
    public GvDataAggregater(DifferenceComparitor<T, D2> comparitor, D1 minimumDifference,
                            CanonicalDatumMaker<T> canonical) {
        mComparitor = comparitor;
        mMinimumDifference = minimumDifference;
        mCanonical = canonical;
    }

    public GvCanonicalCollection<T> aggregate(GvDataList<T> data) {
        GvDataType<T> elementType = data.getGvDataType();

        GvList allocated = new GvList();
        GvCanonicalCollection<T> results = new GvCanonicalCollection<>(elementType);
        for (T reference : data) {
            if (allocated.contains(reference)) continue;
            GvDataList<T> similar = FactoryGvData.newDataList(elementType, data.getReviewId());
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

            results.add(new GvCanonical<>(mCanonical.getCanonical(similar), similar));
        }

        return results;
    }
}


