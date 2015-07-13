/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 13 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.FactoryGvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 13/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class Aggregator<T extends GvData> {
    private GvDataList<T> mData;

    public Aggregator(GvDataList<T> data) {
        mData = data;
    }

    public <D> Map<T, GvDataList<T>> aggregate
            (DifferenceComparitor<T, DifferenceLevel<D>> comparitor,
                    D minimumDifference, CanonicalDatumMaker<T> canonical) {
        Map<T, GvDataList<T>> map = new LinkedHashMap<>();
        GvDataType<T> type = mData.getGvDataType();
        GvDataList<T> data = FactoryGvData.newDataList(type);
        for (T reference : data) {
            GvDataList<T> similar = FactoryGvData.newDataList(type, mData.getReviewId());
            for (T candidate : mData) {
                DifferenceLevel<D> difference = comparitor.compare(reference, candidate);
                if (difference.lessThanOrEqualTo(minimumDifference)) {
                    similar.add(candidate);
                }
            }
            T canon = canonical.getCanonical(similar);
            map.put(canon, similar);
        }

        return map;
    }
}
