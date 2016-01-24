/*
 * Copyright (c) Rizwan Choudrey 2016 - All Rights Reserved
 * Unauthorized copying of this file via any medium is strictly prohibited
 * Proprietary and confidential
 * rizwan.choudrey@gmail.com
 *
 */

package com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault.Plugin;

import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.DataAggregatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.reviewer.PlugIns.DataAggregatorsPlugin.DataAggregationDefault
        .Implementation.ComparitorLevenshteinDistance;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregatorsDefault implements DataAggregatorsPlugin {
    private static final FactoryDataAggregator FACTORY
            = new FactoryDataAggregatorDefault(new ComparitorLevenshteinDistance());

    @Override
    public FactoryDataAggregator getAggregatorFactory() {
        return FACTORY;
    }
}
