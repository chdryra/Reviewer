package com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .FactoryVhDataCollection;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Adapter.ReviewAdapterModel.ReviewBuilding.Interfaces
        .WrapperGridData;
import com.chdryra.android.reviewer.View.GvDataModel.GvData;
import com.chdryra.android.reviewer.View.GvDataModel.GvDataType;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridUi implements WrapperGridData<RvaGridCell, ReviewBuilderAdapter> {

    private final ArrayList<GvDataType<? extends GvData>> mCells;
    private final RvaGridCellList mWrapper;
    private final FactoryVhDataCollection mVhFactory;

    public BuildScreenGridUi(FactoryVhDataCollection vhFactory) {
        mWrapper = new RvaGridCellList();
        mVhFactory = vhFactory;
        mCells = new ArrayList<>();
    }

    public <T extends GvData> void addGridCell(GvDataType<T> dataType) {
        mCells.add(dataType);
    }

    @Override
    public void setSourceAdapter(ReviewBuilderAdapter adapter) {
        for(GvDataType<? extends GvData> dataType : mCells) {
            mWrapper.addNewGridCell(dataType, adapter.getDataBuilderAdapter(dataType), mVhFactory);
        }
    }

    @Override
    public RvaGridCellList getGridWrapper() {
        return mWrapper;
    }
}
