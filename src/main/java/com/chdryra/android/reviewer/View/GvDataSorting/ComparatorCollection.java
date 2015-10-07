package com.chdryra.android.reviewer.View.GvDataSorting;

import com.chdryra.android.reviewer.View.GvDataModel.GvData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/09/2015
 * Email: rizwan.choudrey@gmail.com
 */
public abstract class ComparatorCollection<T extends GvData> {
    private ArrayList<Comparator<T>> mComparators;
    private int mIndex = 0;

    protected ComparatorCollection(@NotNull Comparator<T> defaultComparator) {
        mComparators = new ArrayList<>();
        add(defaultComparator);
    }

    //public methods
    public Comparator<T> getDefault() {
        return mComparators.get(0);
    }

    public Comparator<T> next() {
        Comparator<T> comparator = mComparators.get(mIndex++);
        if (mIndex == mComparators.size()) mIndex = 0;
        return comparator;
    }

    public int size() {
        return mComparators.size();
    }

    protected void add(Comparator<T> comparator) {
        mComparators.add(comparator);
    }
}
