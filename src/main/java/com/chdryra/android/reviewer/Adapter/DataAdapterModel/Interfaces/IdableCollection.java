package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

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

    void addCollection(IdableCollection<T> data);

    @Override
    Iterator<T> iterator();
}
