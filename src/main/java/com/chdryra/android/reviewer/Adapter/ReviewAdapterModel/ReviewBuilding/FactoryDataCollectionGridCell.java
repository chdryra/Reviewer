package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewViewAdapter;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class FactoryDataCollectionGridCell {
    public <T extends GvData> DataCollectionGridCell<T>
    newGridCell(GvDataType<T> dataType, ReviewViewAdapter<T> adapter,
                FactoryVhDataCollection viewHolderFactory) {
        return new DataCollectionGridCell<>(dataType, adapter, viewHolderFactory);
    }
}
