package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

import java.util.Iterator;

/**
 * Created by: Rizwan Choudrey
 * On: 10/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface IdableCollection<T> extends DataReview, Iterable<T> {
    int size();

    @Override
    String getReviewId();

    @Override
    Iterator<T> iterator();
}
