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

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorCollectionImpl<T> implements ComparatorCollection<T> {
    private final ArrayList<NamedComparator<T>> mComparators;
    private int mIndex = 0;

    public ComparatorCollectionImpl(@NotNull NamedComparator<T> defaultComparator) {
        mComparators = new ArrayList<>();
        add(defaultComparator);
    }

    @Override
    public NamedComparator<T> getDefault() {
        return mComparators.get(0);
    }

    @Override
    public NamedComparator<T> next() {
        NamedComparator<T> comparator = mComparators.get(mIndex++);
        if (mIndex == mComparators.size()) mIndex = 0;
        return comparator;
    }

    @Override
    public int size() {
        return mComparators.size();
    }

    @Override
    public ArrayList<NamedComparator<T>> asList() {
        return mComparators;
    }

    @Override
    public NamedComparator<T> moveToComparator(String name) {
        int newIndex;
        for(newIndex = 0; newIndex < mComparators.size(); ++newIndex) {
            NamedComparator<T> comparator = mComparators.get(newIndex);
            if(comparator.getName().equals(name)) {
                mIndex = newIndex;
                break;
            }
        }

        return next();
    }

    public void add(NamedComparator<T> comparator) {
        mComparators.add(comparator);
    }
}
