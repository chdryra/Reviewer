package com.chdryra.android.reviewer.DataSorting.Interfaces;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface ComparatorCollection<T> {
    int size();

    Comparator<T> next();

    //public methods
    Comparator<T> getDefault();
}
