package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseIdableCollection<T extends VerboseDataReview> extends
        IdableCollection<T>, VerboseData {
    @Override
    String getStringSummary();

    @Override
    boolean hasElements();

    @Override
    boolean isVerboseCollection();

    @Override
    int size();

    @Override
    T getItem(int position);

    @Override
    void add(T datum);

    @Override
    void addCollection(IdableCollection<T> data);

    @Override
    Iterator<T> iterator();
}
