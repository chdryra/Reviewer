package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataList;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Encapsulates the range of responses and displays available to each data tile depending
 * on the underlying data and user interaction.
 */
public interface AdapterGridUi<A extends ReviewViewAdapter> {
    void setViewAdapter(A adapter);
    <T extends GvData> void addGridCell(GvDataType<T> dataType);
    GvDataList getGridUi();
}
