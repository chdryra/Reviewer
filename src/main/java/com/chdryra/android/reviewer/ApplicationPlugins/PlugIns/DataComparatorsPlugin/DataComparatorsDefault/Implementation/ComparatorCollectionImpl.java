/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import com.chdryra.android.reviewer.Algorithms.DataSorting.ComparatorCollection;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorCollectionImpl<T> implements ComparatorCollection<T> {
    private final ArrayList<Comparator<T>> mComparators;
    private int mIndex = 0;

    public ComparatorCollectionImpl(@NotNull Comparator<T> defaultComparator) {
        mComparators = new ArrayList<>();
        add(defaultComparator);
    }

    @Override
    public Comparator<T> getDefault() {
        return mComparators.get(0);
    }

    @Override
    public Comparator<T> next() {
        Comparator<T> comparator = mComparators.get(mIndex++);
        if (mIndex == mComparators.size()) mIndex = 0;
        return comparator;
    }

    @Override
    public int size() {
        return mComparators.size();
    }

    protected void add(Comparator<T> comparator) {
        mComparators.add(comparator);
    }
}
