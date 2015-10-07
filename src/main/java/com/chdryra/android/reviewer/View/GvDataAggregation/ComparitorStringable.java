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
    private static final ComparitorString COMPARITOR = new ComparitorString();
    private DataGetter<T, String> mGetter;

    //Constructors
    public ComparitorStringable(DataGetter<T, String> getter) {
        mGetter = getter;
    }

    //Overridden
    @Override
    public DifferencePercentage compare(T lhs, T rhs) {
        return COMPARITOR.compare(mGetter.getData(lhs), mGetter.getData(rhs));
    }
}
