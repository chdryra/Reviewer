/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Implementation;



import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataLocation;
import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.ItemGetter;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorLocationName extends ComparatorStringable<DataLocation> {
    public ComparatorLocationName(ComparatorString comparitor) {
        super(comparitor, new ItemGetter<DataLocation, String>() {
            @Override
            public String getItem(DataLocation datum) {
                return datum.getName().toLowerCase();
            }
        });
    }
}
