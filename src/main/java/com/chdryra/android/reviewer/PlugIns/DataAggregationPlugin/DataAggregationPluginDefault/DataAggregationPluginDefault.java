package com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault;

import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.Api.DataAggregationPlugin;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.Api.FactoryDataAggregator;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.ComparitorLevenshteinDistance;
import com.chdryra.android.reviewer.PlugIns.DataAggregationPlugin.DataAggregationPluginDefault
        .Implementation.FactoryDataAggregatorDefault;

/**
 * Created by: Rizwan Choudrey
 * On: 20/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataAggregationPluginDefault implements DataAggregationPlugin {
    private static final FactoryDataAggregator FACTORY
            = new FactoryDataAggregatorDefault(new ComparitorLevenshteinDistance());
    @Override
    public FactoryDataAggregator getAggregatorFactory() {
        return FACTORY;
    }
}
