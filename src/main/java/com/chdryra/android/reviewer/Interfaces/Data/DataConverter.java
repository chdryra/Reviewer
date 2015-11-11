package com.chdryra.android.reviewer.Interfaces.Data;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataConverter<T1, T2 extends DataReview> {
    T2 convert(T1 datum);

    T2 convert(T1 datum, String reviewId);

    IdableList<T2> convert(Iterable<? extends T1> data, String reviewId);

    IdableList<T2> convert(IdableList<? extends T1> data);
}
