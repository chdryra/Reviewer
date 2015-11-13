package com.chdryra.android.reviewer.Interfaces.Data;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 13/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface VerboseData extends Validatable{
    String getStringSummary();

    boolean hasElements();

    boolean isVerboseCollection();

    @Override
    boolean hasData(DataValidator dataValidator);
}
