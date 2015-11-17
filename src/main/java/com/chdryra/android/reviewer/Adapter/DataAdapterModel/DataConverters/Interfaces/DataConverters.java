package com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataConverters.Implementation.MdConverters.ConverterMd;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataConverters {
    ConverterMd getMdConverter();
    ConverterGv getGvConverter();
}
