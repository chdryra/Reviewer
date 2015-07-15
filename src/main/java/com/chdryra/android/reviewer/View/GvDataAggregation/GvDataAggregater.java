/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataMap;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;
import com.chdryra.android.reviewer.View.GvDataModel.GvList;

/**
 * Created by: Rizwan Choudrey
 * On: 13/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class GvDataAggregater<T extends GvData> {
    private GvDataList<T> mData;

    public GvDataAggregater(GvDataList<T> data) {
        mData = data;
    }

    public <D1, D2 extends DifferenceLevel<D1>> GvDataMap<T, GvDataList<T>> aggregate
            (DifferenceComparitor<T, D2> comparitor,
             D1 minimumDifference, CanonicalDatumMaker<T> canonical) {
        //TODO make type safe
        GvDataType listType = mData.getGvDataType();
        GvList allocated = new GvList();
        GvDataMap<T, GvDataList<T>> map =
                new GvDataMap<>(listType.getElementType(), listType, mData.getReviewId());
        for (T reference : mData) {
            if(allocated.contains(reference)) continue;
            GvDataList<T> similar = FactoryGvData.newDataList(listType, mData.getReviewId());
            for (T candidate : mData) {
                D2 difference = comparitor.compare(reference, candidate);
                if (difference.lessThanOrEqualTo(minimumDifference)) {
                    similar.add(candidate);
                    allocated.add(candidate);
                }
            }
            T canon = canonical.getCanonical(similar);
            map.put(canon, similar);
        }

        return map;
    }
}
