package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataListImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 15/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface FactoryGridUi<GC extends GvDataListImpl> {
    BuildScreenGridUi<GC> newGridUiWrapper(FactoryVhDataCollection vhFactory);
}
