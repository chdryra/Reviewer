/*
 * Copyright (c) 2015, Rizwan Choudrey - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Author: Rizwan Choudrey
 * Date: 7 July, 2015
 */

package com.chdryra.android.reviewer.View.GvDataAggregation;

/**
 * Created by: Rizwan Choudrey
 * On: 07/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparitorStringable<T> implements DifferenceComparitor<T, DifferencePercentage> {

    private DataGetter<T, String> mGetter;

    public ComparitorStringable(DataGetter<T, String> getter) {
        mGetter = getter;
    }

    @Override
    public DifferencePercentage compare(T lhs, T rhs) {
        ComparitorString comparitor = new ComparitorString();
        return comparitor.compare(mGetter.getData(lhs), mGetter.getData(rhs));
    }
}
