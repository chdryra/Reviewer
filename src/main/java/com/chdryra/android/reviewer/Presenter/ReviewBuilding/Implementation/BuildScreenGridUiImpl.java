package com.chdryra.android.reviewer.Presenter.ReviewBuilding.Implementation;

import com.chdryra.android.reviewer.Presenter.Interfaces.Data.GvData;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.BuildScreenGridUi;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.FactoryVhDataCollection;
import com.chdryra.android.reviewer.Presenter.ReviewBuilding.Interfaces.ReviewBuilderAdapter;
import com.chdryra.android.reviewer.Presenter.ReviewViewModel.Implementation.Data.GvData.GvDataType;

import java.util.ArrayList;

/**
 * Created by: Rizwan Choudrey
 * On: 11/11/2015
 * Email: rizwan.choudrey@gmail.com
 */
public class BuildScreenGridUiImpl implements BuildScreenGridUi<DataBuilderGridCell> {
    private final ArrayList<GvDataType<?>> mCells;
    private final DataBuilderGridCellList mWrapper;
    private final FactoryVhDataCollection mVhFactory;

    public BuildScreenGridUiImpl(FactoryVhDataCollection vhFactory) {
        mWrapper = new DataBuilderGridCellList();
        mVhFactory = vhFactory;
        mCells = new ArrayList<>();
    }

    public <T extends GvData> void addGridCell(GvDataType<T> dataType) {
        mCells.add(dataType);
    }

    @Override
    public void setParentAdapter(ReviewBuilderAdapter<?> adapter) {
        for(GvDataType<?> dataType : mCells) {
            mWrapper.addNewGridCell(adapter.getDataBuilderAdapter(dataType), mVhFactory);
        }
    }

    @Override
    public DataBuilderGridCellList getGridWrapper() {
        return mWrapper;
    }
}
