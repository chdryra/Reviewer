package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryGridUi<T extends GvData, A extends ReviewViewAdapter> {
    WrapperGridData<T, A> newGridUiWrapper(FactoryVhDataCollection vhFactory);
}
