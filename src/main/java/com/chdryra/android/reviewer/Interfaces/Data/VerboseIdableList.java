package com.chdryra.android.reviewer.Interfaces.Data;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseIdableList<T extends VerboseDataReview>
        extends VerboseIdableCollection<T>, IdableList<T>{
    @Override
    String getReviewId();

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
    void add(IdableCollection<T> data);

    @Override
    Iterator<T> iterator();
}
