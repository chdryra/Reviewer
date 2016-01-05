/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 8 July, 2015
 */

package com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Implementation;

import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataAlgorithms.DataAggregation.Interfaces.ItemGetter;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by: Rizwan Choudrey
 * On: 08/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ItemCounter<T, D> {
    private ItemGetter<T, D> mGetter;
    private Map<D, Integer> mCountMap;
    private D mMaxItem;
    private int mNonMaxCount;

    public ItemCounter(ItemGetter<T, D> getter) {
        mCountMap = new LinkedHashMap<>();
        mGetter = getter;
    }

    public D getModeItem() {
        return mMaxItem;
    }

    public int getNonModeCount() {
        return mNonMaxCount;
    }

    public int getCount() {
        return mCountMap.size();
    }

    public void performCount(Iterable<? extends T> data) {
        mCountMap.clear();
        countAndSetMaxItem(data);
        mNonMaxCount = mCountMap.size() - 1;
    }

    private void countAndSetMaxItem(Iterable<? extends T> data) {
        mMaxItem = mGetter.getData(data.iterator().next());
        int maxCountSoFar = 1;
        for (T datum : data) {
            D item = mGetter.getData(datum);
            if (item == null) continue;
            Integer numberOfItems = incrementCount(item);
            maxCountSoFar = setNewMaxIfNeccessary(maxCountSoFar, numberOfItems, item);
        }
    }

    private int setNewMaxIfNeccessary(int maxCount, Integer numberOfItems, D item) {
        if (numberOfItems > maxCount) {
            maxCount = numberOfItems;
            mMaxItem = item;
        }
        return maxCount;
    }

    @NonNull
    private Integer incrementCount(D item) {
        Integer num = mCountMap.get(item);
        Integer numberOfItems = num == null ? 1 : num + 1;
        mCountMap.put(item, numberOfItems);
        return numberOfItems;
    }
}
