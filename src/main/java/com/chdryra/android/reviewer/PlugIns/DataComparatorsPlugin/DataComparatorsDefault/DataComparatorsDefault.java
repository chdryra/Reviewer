package com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault;

import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.DataComparatorsPlugin;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.Api.FactoryDataComparators;
import com.chdryra.android.reviewer.PlugIns.DataComparatorsPlugin.DataComparatorsDefault
        .Implementation.FactoryDataComparatorsDefault;

/**
 * Created by: Rizwan Choudrey
 * On: 22/01/2016
 * Email: rizwan.choudrey@gmail.com
 */
public class DataComparatorsDefault implements DataComparatorsPlugin{
    private static final FactoryDataComparators FACTORY = new FactoryDataComparatorsDefault();
    @Override
    public FactoryDataComparators getComparatorsFactory() {
        return FACTORY;
    }
}
