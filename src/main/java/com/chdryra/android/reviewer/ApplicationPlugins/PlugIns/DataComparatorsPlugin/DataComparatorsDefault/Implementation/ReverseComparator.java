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
 * On: 29/11/2016
 * Email: rizwan.choudrey@gmail.com
 */

public class ReverseComparator<T> implements Comparator<T> {
    private final Comparator<T> mComparator;

    public ReverseComparator(Comparator<T> comparator) {
        mComparator = comparator;
    }

    @Override
    public int compare(T lhs, T rhs) {
        return -mComparator.compare(lhs, rhs);
    }
}
