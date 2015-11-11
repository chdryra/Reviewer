package com.chdryra.android.reviewer.Interfaces.Data;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableCollection<T extends DataReview> extends Iterable<T> {
    int size();

    T getItem(int position);

    void add(T datum);

    void add(IdableCollection<T> data);

    T get(String reviewId);

    boolean contains(String reviewId);

    @Override
    Iterator<T> iterator();
}
