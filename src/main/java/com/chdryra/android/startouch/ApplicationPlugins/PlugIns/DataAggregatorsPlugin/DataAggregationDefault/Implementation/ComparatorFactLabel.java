/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.startouch.ApplicationPlugins.PlugIns.DataAggregatorsPlugin
        .DataAggregationDefault.Implementation;


import com.chdryra.android.corelibrary.Aggregation.ComparatorString;
import com.chdryra.android.corelibrary.Aggregation.ItemGetter;
import com.chdryra.android.startouch.DataDefinitions.Data.Interfaces.DataFact;

/**
 * Created by: Rizwan Choudrey
 * On: 03/07/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ComparatorFactLabel extends ComparatorStringable<DataFact> {
    public ComparatorFactLabel(ComparatorString comparitor) {
        super(comparitor, new ItemGetter<DataFact, String>() {
            @Override
            public String getItem(DataFact datum) {
                return datum.getLabel().toLowerCase();
            }
        });
    }
}
