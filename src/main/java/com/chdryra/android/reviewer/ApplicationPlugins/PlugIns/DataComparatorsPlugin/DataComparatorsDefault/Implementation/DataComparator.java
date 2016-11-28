/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.ApplicationPlugins.PlugIns.DataComparatorsPlugin.DataComparatorsDefault.Implementation;

import java.util.Comparator;

/**
 * Created by: Rizwan Choudrey
 * On: 27/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparator<ObjectType, DataType> implements Comparator<ObjectType> {
    private final Comparator<DataType> mComparator;
    private final DataGetter<ObjectType, ? extends DataType> mGetter;

    public DataComparator(Comparator<DataType> comparator, DataGetter<ObjectType, ? extends DataType> getter) {
        mComparator = comparator;
        mGetter = getter;
    }

    @Override
    public int compare(ObjectType lhs, ObjectType rhs) {
        return mComparator.compare(mGetter.getData(lhs), mGetter.getData(rhs));
    }
}
