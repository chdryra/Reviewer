/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAlgorithms;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorStringable<T> implements DifferenceComparitor<T,
        DifferencePercentage> {

    private StringGetter<T> mGetter;

    public interface StringGetter<T> {
        String getString(T datum);
    }

    public ComparitorStringable(StringGetter<T> getter) {
        mGetter = getter;
    }

    @Override
    public DifferencePercentage compare(T lhs, T rhs) {
        ComparitorString comparitor = new ComparitorString();
        return comparitor.compare(mGetter.getString(lhs), mGetter.getString(rhs));
    }
}
