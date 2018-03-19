/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import android.support.annotation.NonNull;

import com.chdryra.android.corelibrary.Aggregation.ItemCounter;
import com.chdryra.android.corelibrary.Aggregation.ItemGetter;
import com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Interfaces.CanonicalDatumMaker;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.HasReviewId;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CanonicalStringMaker<T extends HasReviewId> implements
        CanonicalDatumMaker<T> {
    @NonNull
    protected abstract ItemGetter<T, String> getStringGetter();

    String getModeString(Iterable<? extends T> data) {
        ItemCounter<T, String> counter = countItems(data);
        String modeString = counter.getModeItem();
        int nonMode = counter.getNonModeCount();
        if (nonMode > 0) modeString = formatModeString(modeString, nonMode);
        return modeString;
    }

    @NonNull
    String formatModeString(String modeString, int nonMode) {
        modeString += " + " + String.valueOf(nonMode);
        return modeString;
    }

    private ItemCounter<T, String> countItems(Iterable<? extends T> data) {
        ItemCounter<T, String> counter = newItemCounter();
        counter.performCount(data);
        return counter;
    }

    @NonNull
    private ItemCounter<T, String> newItemCounter() {
        return new ItemCounter<>(getStringGetter());
    }
}
