/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DatumCounter<T extends GvData, D> {
    private Map<D, Integer> mCountMap;
    private D               mMaxItem;
    private int             mMaxCount;
    private int             mNonMaxCount;

    public DatumCounter(GvDataList<T> data, DataGetter<T, D> getter) {
        mCountMap = new LinkedHashMap<>();
        for (T datum : data) {
            D item = getter.getData(datum);
            if (item == null) continue;
            Integer num = mCountMap.get(item);
            num = num == null ? 1 : num + 1;
            mCountMap.put(item, num);
        }

        mMaxCount = 0;
        mMaxItem = getter.getData(data.getItem(0));
        for (D key : mCountMap.keySet()) {
            int num = mCountMap.get(key);
            if (num > mMaxCount) {
                mMaxCount = num;
                mMaxItem = key;
            }
        }

        mNonMaxCount = mCountMap.size() - 1;
    }

    public Map<D, Integer> getCountMap() {
        return mCountMap;
    }

    public D getMaxItem() {
        return mMaxItem;
    }

    public int getMaxCount() {
        return mMaxCount;
    }

    public int getNonMaxCount() {
        return mNonMaxCount;
    }

    public int getCount() {
        return mCountMap.size();
    }
}
