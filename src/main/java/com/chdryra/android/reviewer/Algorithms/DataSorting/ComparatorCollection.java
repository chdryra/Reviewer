/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.Algorithms.DataSorting;

import com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin
        .DataComparatorsDefault.Implementation.NamedComparator;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ComparatorCollection<T> {
    int size();

    NamedComparator<T> next();

    NamedComparator<T> getDefault();

    ArrayList<String> getComparatorNames();

    NamedComparator<T> moveToComparator(String name);
}
