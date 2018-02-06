/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceBoolean;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataImage;
import com.chdryra.android.mygenerallibrary.Aggregation.DifferenceComparator;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorImageBitmap implements DifferenceComparator<DataImage, DifferenceBoolean> {
    @Override
    public DifferenceBoolean compare(DataImage  lhs, DataImage rhs) {
        return new DifferenceBoolean(!lhs.getBitmap().sameAs(rhs.getBitmap()));
    }
}
