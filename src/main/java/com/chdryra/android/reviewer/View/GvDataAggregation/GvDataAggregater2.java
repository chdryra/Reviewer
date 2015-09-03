package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonical;
import com.chdryra.android.reviewer.View.GvDataModel.GvCanonicalList;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 02/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregater2<T extends GvData, D1, D2 extends DifferenceLevel<D1>> {
    private DifferenceComparitor<T, D2> mComparitor;
    private D1 mMinimumDifference;
    private CanonicalDatumMaker<T> mCanonical;

    public GvDataAggregater2(DifferenceComparitor<T, D2> comparitor, D1 minimumDifference,
                             CanonicalDatumMaker<T> canonical) {
        mComparitor = comparitor;
        mMinimumDifference = minimumDifference;
        mCanonical = canonical;
    }

    public GvCanonicalList<T> aggregate(GvDataList<T> data) {
        GvDataType listType = data.getGvDataType();

        GvList allocated = new GvList();
        //TODO mae type safe
        GvCanonicalList<T> results = new GvCanonicalList<>(listType.getElementType());
        for (T reference : data) {
            if (allocated.contains(reference)) continue;
            //TODO make type safe
            GvDataList<T> similar = FactoryGvData.newDataList(listType, data.getReviewId());
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


