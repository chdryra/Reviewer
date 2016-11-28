/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FloatComparator implements Comparator<Float> {
    private final Ordering mOrdering;

    public FloatComparator(Ordering ordering) {
        mOrdering = ordering;
    }

    @Override
    public int compare(Float lhs, Float rhs) {
        if(lhs < rhs) return -mOrdering.getFactor();
        if(rhs > lhs) return mOrdering.getFactor();
        return 0;
    }
}
