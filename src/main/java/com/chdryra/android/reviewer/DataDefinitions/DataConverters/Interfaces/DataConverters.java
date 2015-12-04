package com.chdryra.android.reviewer.DataDefinitions.DataConverters.Interfaces;

import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.GvConverters.ConverterGv;
import com.chdryra.android.reviewer.DataDefinitions.DataConverters.Implementation.MdConverters.ConverterMd;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface DataConverters {
    ConverterMd getMdConverter();
    ConverterGv getGvConverter();
}
