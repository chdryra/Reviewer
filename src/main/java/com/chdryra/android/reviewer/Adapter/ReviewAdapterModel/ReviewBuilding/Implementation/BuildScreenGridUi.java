package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces.WrapperGridData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridUi implements WrapperGridData<RvaGridCell, ReviewBuilderAdapter> {

    private ReviewBuilderAdapter mBuilder;
    private final RvaGridCellList mWrapper;
    private final FactoryVhDataCollection mVhFactory;

    public BuildScreenGridUi(FactoryVhDataCollection vhFactory) {
        mWrapper = new RvaGridCellList();
        mVhFactory = vhFactory;
    }

    public <T extends GvData> void wrapGridCell(GvDataType<T> dataType) {
        if(mBuilder != null) {
            //TODO make type safe
            mWrapper.addNewGridCell(dataType, mBuilder.getDataBuilderAdapter(dataType), mVhFactory);
        }
    }

    @Override
    public void setSourceAdapter(ReviewBuilderAdapter adapter) {
        mBuilder = adapter;
    }

    @Override
    public RvaGridCellList getGridWrapper() {
        return mWrapper;
    }
}
