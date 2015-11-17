package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters
        .ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces
        .DataConverters;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters
        .ConverterMd;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConvertersMdGv implements DataConverters {
    private ConverterMd mConverterMd;
    private ConverterGv mConverterGv;

    public ConvertersMdGv(ConverterMd converterMd, ConverterGv converterGv) {
        mConverterMd = converterMd;
        mConverterGv = converterGv;
    }

    @Override
    public ConverterMd getMdConverter() {
        return mConverterMd;
    }

    @Override
    public ConverterGv getGvConverter() {
        return mConverterGv;
    }
}
