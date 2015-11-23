package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataListImpl;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BuildScreenGridUi<GC extends GvDataListImpl> {

    void setParentAdapter(ReviewBuilderAdapter<?> adapter);

    GvDataListImpl<GC> getGridWrapper();
}
