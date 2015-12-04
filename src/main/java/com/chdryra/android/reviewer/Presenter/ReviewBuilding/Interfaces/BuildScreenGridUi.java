package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvDataList;

/**
 * Created by: Rizwan Choudrey
 * On: 20/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public interface BuildScreenGridUi<GC extends GvDataList> {

    void setParentAdapter(ReviewBuilderAdapter<?> adapter);

    GvDataList<GC> getGridWrapper();
}
