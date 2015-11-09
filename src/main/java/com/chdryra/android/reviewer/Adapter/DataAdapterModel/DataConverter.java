package com.chdryra.android.reviewer.Adapter.DataAdapterModel;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataConverter<T1, T2> {
    T2 convert(T1 datum);

    Iterable<T2> convert(Iterable<T1> data, String reviewId);
}
