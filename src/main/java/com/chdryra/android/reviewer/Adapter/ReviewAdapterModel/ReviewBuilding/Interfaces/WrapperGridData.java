package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public interface WrapperGridData<U extends GvData, A extends ReviewViewAdapter> {
    void setSourceAdapter(A adapter);
    GvDataList<U> getGridWrapper();
}
