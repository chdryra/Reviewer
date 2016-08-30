/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import android.support.annotation.NonNull;

import com.chdryra.android.reviewer.DataDefinitions.Data.Interfaces.HasReviewId;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.CanonicalDatumMaker;
import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Interfaces.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 06/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class CanonicalStringMaker<T extends HasReviewId> implements
        CanonicalDatumMaker<T> {
    @NonNull
    protected abstract ItemGetter<T, String> getStringGetter();

    protected String getModeString(Iterable<? extends T> data) {
        ItemCounter<T, String> counter = countItems(data);
        String modeString = counter.getModeItem();
        int nonMode = counter.getNonModeCount();
        if (nonMode > 0) modeString = formatModeString(modeString, nonMode);
        return modeString;
    }

    @NonNull
    protected String formatModeString(String modeString, int nonMode) {
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
