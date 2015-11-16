package com.chdryra.android.reviewer.Adapter.DataAdapterModel.Interfaces;

import com.chdryra.android.reviewer.Adapter.DataAdapterModel.Implementation.DataValidator;

/**
 * Created by: Rizwan Choudrey
 * On: 09/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface Validatable {
    boolean hasData(DataValidator validator);
}
