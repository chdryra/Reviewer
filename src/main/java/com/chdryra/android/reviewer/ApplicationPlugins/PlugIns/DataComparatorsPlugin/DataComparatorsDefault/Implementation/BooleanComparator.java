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
 * On: 28/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class BooleanComparator implements Comparator<Boolean> {
    private final Ordering mOrdering;

    public BooleanComparator(Ordering ordering) {
        mOrdering = ordering;
    }

    @Override
    public int compare(Boolean lhs, Boolean rhs) {
        //false then true if ascending
        int comp = 0;
        if (!lhs && rhs) {
            comp = -mOrdering.getFactor();
        } else if (!rhs && lhs) {
            comp = 1;
        }

        return comp;
    }
}
