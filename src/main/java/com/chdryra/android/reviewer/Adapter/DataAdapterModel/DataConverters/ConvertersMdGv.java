package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class ConvertersMdGv implements DataConverters{
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
