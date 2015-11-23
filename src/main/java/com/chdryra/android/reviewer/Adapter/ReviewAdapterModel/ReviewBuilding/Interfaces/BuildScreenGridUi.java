package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.View.GvDataModel.Implementation.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BuildScreenGridUi<GC extends GvDataList> {

    void setParentAdapter(ReviewBuilderAdapter<?> adapter);

    GvDataList<GC> getGridWrapper();
}
